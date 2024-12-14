from flask import Flask,jsonify,request
import requests
from datetime import datetime
from io import BytesIO
import base64
import qrcode
from eureka_config import *
from py_eureka_client.eureka_client import EurekaClient

from settings import *
from flask_cors import CORS

app = Flask(__name__)

CORS(app, resources={r"/*": {"origins": "*"}}, supports_credentials=True)

client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)

from uuid import UUID

# Route pour la creation d'un code qr
@app.route('/payment/create_order', methods=['POST'])
def create_order():
    """
    La fonction crée une commande, génère un QR code, et met à jour le solde du client.
    """
    try:
        # Récupération des données de la requête
        data = request.get_json()
        if not data:
            return jsonify({"error": "Aucune donnée reçue."}), 400

        amount =int(data.get('amount'))
        commandeId = data.get('commandeId')

        # Validation des données
        if not commandeId or not isinstance(amount, int) or amount <= 0:
            return jsonify({"error": "Données erronées. Vérifiez les IDs et le montant."}), 400

        try:
            UUID(commandeId)
        except ValueError:
            return jsonify({"error": "Les IDs doivent être des UUID valides."}), 400

        # Récupérer la commande associée
        SpringUrlGet = f"http://37.60.244.227:8001/dao/commandes/{commandeId}"
        commande = requests.get(SpringUrlGet, timeout=5)
        commande.raise_for_status()
        commande_data = commande.json()
        userId = commande_data['idUser']
        prestataireId = commande_data['idPrestataire']

        # Vérifier le solde du client
        SpringUrlGet = f"http://37.60.244.227:8001/dao/users/{userId}"
        client = requests.get(SpringUrlGet, timeout=5)
        client.raise_for_status()
        client_data = client.json()
        montant = client_data.get('montantCompte')

        if montant is None:
            return jsonify({"error": "Le compte utilisateur n'a pas de montant défini."}), 400
        if montant < amount:
            return jsonify({"message": "Montant insuffisant."}), 400

        # Mettre à jour le solde du client
        montant -= amount
        SpringURL = f"http://37.60.244.227:8001/dao/users/{userId}/update-montant"
        response = requests.patch(SpringURL, json={"montant": montant}, timeout=5)
        response.raise_for_status()

        app.logger.info(f"Montant mis à jour pour l'utilisateur {userId}. Nouveau solde : {montant}")

        # Générer le QR code
        current_datetime = datetime.now()
        qr_data = f"Prestataire:{prestataireId}, Amount: {amount}, Date:{current_datetime}, Commande:{commandeId}"
        qr = qrcode.make(qr_data)
        img_io = BytesIO()
        qr.save(img_io, 'PNG')
        img_io.seek(0)
        qr_b64 = base64.b64encode(img_io.getvalue()).decode('utf-8')

        # Enregistrer la commande dans la base de données
        try:
            with conn.cursor() as cursor:
                cursor.execute(
                    "INSERT INTO orders (prestataireid, amount, qr_code, commandeid) VALUES (%s, %s, %s, %s)",
                    (prestataireId, amount, qr_b64, commandeId)
                )
                conn.commit()
                app.logger.info(f"Commande insérée pour le prestataire {prestataireId}")
        except psycopg2.Error as e:
            app.logger.error(f"Erreur SQL : {str(e)}")
            return jsonify({"error": "Erreur lors de l'enregistrement de la commande."}), 500

        # Retourner le QR code au client
        return jsonify({
            "message": "Commande payée avec succès.",
            "clientId": userId,
            "amount": amount,
            "code": qr_b64
        }), 200

    except requests.exceptions.RequestException as e:
        app.logger.error(f"Erreur externe : {str(e)}")
        return jsonify({"error": f"Erreur lors de la communication avec le service externe : {str(e)}"}), 502
    except Exception as e:
        app.logger.error(f"Erreur inattendue : {str(e)}")
        return jsonify({"error": "Erreur interne du serveur."}), 500

# Route pour afficher les qr des commandes d'un client
'''
    Elle recupere l'id de la commande et affiche le code qr associer en cours 
'''
@app.route('/payment/show_qr', methods=['POST'])
def show_qr():
    try:
        commande_id = request.json['commandeId']
        cursor.execute("SELECT qr_code FROM orders WHERE commandeId = %s and etat = 'en cours'", (commande_id,))
        qr_codes = cursor.fetchall()
        return jsonify({
            "qr_codes":qr_codes
        }),200
    except requests.exceptions.RequestException as e:
        return jsonify({"error": f"Erreur lors de la récupération des coordonnées : {str(e)}"}), 500

# Veification de code qr

'''
    Ce service utilise deux donnee envoyer en post:
    - le code_qr(qr_code): qui est le text associer au code_qr scanner
    - l'id du prestataire qui scanne le code(prestataireId)
'''
@app.route('/payment/verification_qr', methods=['POST'])
def verification():
    if request.method == 'POST':
        data = request.get_json()
        qr_code = data.get('qr_code')
        prestataireId = data.get('prestataireId')

        # Vérification UUID
        try:
            UUID(prestataireId)  # Vérifie que prestataireId est un UUID valide
        except ValueError:
            return jsonify({"error": "L'ID doit être un UUID valide."}), 400

        if prestataireId:
            try:
                with conn.cursor() as cursor:
                    cursor.execute("SELECT * FROM orders WHERE qr_code = %s AND etat = 'en cours'", (qr_code,))
                    orders = cursor.fetchone()

                    if orders:
                        if orders['prestataireid'] == prestataireId:
                            cursor.execute("UPDATE orders SET etat = 'validé' WHERE id = %s", (orders['id'],))
                            conn.commit()

                            # Appeler le service externe pour récupérer le montant actuel du prestataire
                            SpringUrlGet = f"http://37.60.244.227:8001/dao/users/{prestataireId}"
                            try:
                                Prestataire = requests.get(SpringUrlGet)
                                Prestataire.raise_for_status()
                                data = Prestataire.json()
                                montant = data.get('montantCompte')
                                montant += orders['amount']

                                # Envoyer le montant mis à jour
                                SpringURL = f"http://37.60.244.227:8001/dao/users/{prestataireId}/update-montant"
                                response = requests.patch(SpringURL, json={"montant": montant})

                                if response.status_code == 200:
                                    return jsonify({
                                        "message": "Commande validée avec succès",
                                        "prestataireId": prestataireId,
                                        "amount": orders['amount']
                                    }), 200
                                else:
                                    app.logger.error(f"Erreur lors de la mise à jour du montant : {response.text}")
                                    return jsonify({"error": "Erreur lors de la mise à jour du montant."}), 502
                            except requests.RequestException as e:
                                app.logger.error(f"Erreur externe : {str(e)}")
                                return jsonify({"error": "Erreur lors de la communication avec le service externe."}), 502
                        else:
                            return jsonify({"message": "Le prestataire n'est pas le bon"}), 403
                    else:
                        return jsonify({"message": "Code QR inconnu ou commande déjà validée"}), 404
            except Exception as e:
                app.logger.error(f"Erreur SQL : {str(e)}")
                return jsonify({"error": "Erreur lors de la vérification dans la base de données."}), 500
        else:
            return jsonify({"message": "Le prestataire ne peut pas être vide"}), 400

@app.route('/payment/suggestion', methods=['POST'])
def suggestionNom():
    data = request.get_json()
    nom = data.get('nom')

    # Validation des données d'entrée
    if not isinstance(nom, str) or not nom.strip():
        return jsonify({"noms": None}), 400  # Bad Request

    SpringURL = "http://37.60.244.227:8001/dao/produits/all"

    try:
        # Requête vers le service Spring
        spring_response = requests.get(SpringURL)
        spring_response.raise_for_status()
        datas = spring_response.json()
    except requests.RequestException:
        return jsonify({"error": "Erreur lors de la communication avec le service externe."}), 502  # Bad Gateway

    # Vérification de la structure de la réponse
    if not isinstance(datas, list):
        return jsonify({"error": "Réponse invalide du service externe."}), 502

    # Filtrage des noms (en vérifiant que chaque élément est bien un dict avec 'libelle')
    noms = [
        produit.get('libelle') 
        for produit in datas 
        if isinstance(produit, dict) and produit.get('libelle', '').lower().startswith(nom.lower())
    ]

    return jsonify({"noms": noms})

import asyncio

async def start_eureka_client():
    await client.start()

if __name__ == "__main__":
    # Enregistrer le service auprès d'Eureka
    loop = asyncio.get_event_loop()
    loop.run_until_complete(start_eureka_client())

    try:
        app.run(host="0.0.0.0", port=EUREKA_PORT, debug=True)
    finally:
        # Désenregistrer le service lors de l'arrêt de l'application
        loop.run_until_complete(client.stop())

