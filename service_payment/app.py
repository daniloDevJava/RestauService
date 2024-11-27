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
@app.route('/create_order', methods=['POST'])
def create_order():
    '''
        La fonction recupere les donnees necessaire pour la creation du code qr
        - id du prestataire
        - id de la commande
        - montant de la commande
    '''
    try :
        prestataireId = request.json['prestataireId']
        amount = int(request.json['amount'])
        commandeId = request.json['commandeId']

        if not prestataireId or not commandeId:
            return jsonify({"error":"donnees erronees"}),400

        try:
            UUID(prestataireId)  # Vérifie que prestataireId est un UUID valide
            UUID(commandeId)
        except ValueError:
            return jsonify({"error": "Les IDs doivent être des UUID valides."}), 400


        # Obtenir la date et l'heure actuelles
        current_datetime = datetime.now()

        qr_data = f"Prestataire:{prestataireId}, Amount: {amount}, date:{current_datetime}, commande:{commandeId}"
        
        # Génération du QR code
        qr = qrcode.make(qr_data)
        img_io = BytesIO()
        qr.save(img_io, 'PNG')
        img_io.seek(0)
        qr_b64 = base64.b64encode(img_io.getvalue()).decode('utf-8')

        # Enregistrer dans la base de données
        cursor.execute(
            "INSERT INTO orders (prestataireid, amount, qr_code, commandeid) VALUES (%s, %s, %s,%s)",
            (prestataireId, amount, qr_b64,commandeId)
        )
        conn.commit()
        # On renvois le code qr genere sous forme json
        '''
            Le code qr generer est sous forme de text
        '''
        return jsonify({
            "code":qr_b64
            }),200
    except requests.exceptions.RequestException as e:
        return jsonify({"error": f"Erreur lors de la récupération des coordonnées : {str(e)}"}), 500

# Route pour afficher les qr des commandes d'un client
'''
    Elle recupere l'id de la commande et affiche le code qr associer en cours 
'''
@app.route('/show_qr', methods=['POST'])
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
@app.route('/verification_qr',methods=['POST'])
def verification():
    if request.method == 'POST':
        data = request.get_json()
        qr_code = data.get('qr_code')
        prestataireId = data.get('prestataireId')

        try:
            UUID(prestataireId)  # Vérifie que prestataireId est un UUID valide
        except ValueError:
            return jsonify({"error": "L'ID doit etre un UUID valide."}), 400

        if prestataireId:
            cursor.execute("SELECT * FROM orders WHERE qr_code = %s and etat = 'en cours'", (qr_code,)) # on recupere le code associer au qr
            orders = cursor.fetchone()
            if orders:
                if orders['prestataireid'] == prestataireId: # On verifie si le prestataire est le bon
                    cursor.execute("UPDATE orders SET etat = 'validé' WHERE id = %s", (orders['id'],))
                    conn.commit()

                    # J'envois l'argent dans le compte du prestataire

                    # SpringURL = "http://37.60.244.227/updateCount"
                    # spring_response = requests.post(SpringURL, json={
                    #                                         "IdPrestataire": prestataireId,
                    #                                         "montant": orders['amount']
                    #                                     }
                    #                                 )
                    return jsonify(
                        {
                            "message": "Commande valide avec succès",
                            # "result":spring_response,
                            "prestataireId":prestataireId,
                            "amount":orders['amount']
                        }
                    ), 200
                else:
                    return jsonify({"message": "Le prestataire n'est pas le bon"}), 403
            else:
                return jsonify(
                    {
                        "message":"Code qr inconnu ou commande deja valider"
                    }
                )
        else:
            return jsonify(
                {
                    "message":"Le prestataire ne peut pas etre vide"
                }
            )
    else:
        return jsonify({
            "message":"Mauvaise methode de request"
        })

@app.route('/suggestion', methods=['POST'])
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