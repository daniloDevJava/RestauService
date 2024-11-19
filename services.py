from flask import Flask, request, jsonify,render_template
import qrcode
from io import BytesIO
import base64
import psycopg2
from psycopg2.extras import RealDictCursor
import math,requests
from datetime import datetime

app = Flask(__name__)

# Fonction pour calculer la distance entre deux points géographiques
'''
    Elle prend en parametre les longitudes et latitudes des personnes
'''
def calculate_distance(lat1, lon1, lat2, lon2): 
    R = 6371  # Rayon de la Terre en kilomètres
    dlat = math.radians(lat2 - lat1)
    dlon = math.radians(lon2 - lon1)
    a = (math.sin(dlat / 2) ** 2 +
         math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) *
         math.sin(dlon / 2) ** 2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return R * c  # Retourne la distance en kilomètres

# Route POST pour calculer la distance
@app.route('/calcul-distance', methods=['POST'])
def calculate_distance_endpoint():
    try:
        # Récupère les données envoyées sous forme de JSON
        data = request.get_json()
        lat1 = data['lat1']
        lon1 = data['lon1']
        lat2 = data['lat2']
        lon2 = data['lon2']

        # Calculer la distance
        distance = calculate_distance(lat1, lon1, lat2, lon2)
        
        # Retourne la distance en réponse JSON
        return jsonify({'distance_km': distance}), 200
    except KeyError:
        return jsonify({'error': 'Invalid input. Make sure lat1, lon1, lat2, and lon2 are provided.'}), 400

# Route pour afficher les coordonnes geographique d'un quartier par son nom en utilisant l'api Nominatim
@app.route('/quartier-coordonnees', methods=['POST'])
def quartier_coordonnees():
    data = request.json
    nom_quartier = data.get('nom')

    if not nom_quartier:
        return jsonify({"error": "Le nom du quartier est requis."}), 400

    # URL de l'API Nominatim pour le géocodage
    url = "https://nominatim.openstreetmap.org/search"
    
    try:
        params = {
            'q': nom_quartier,
            'format': 'json',
            'addressdetails': 1,
            'limit': 1  # Limiter à un seul résultat
        }
        
        # Faire la requête à l'API Nominatim avec un en-tête User-Agent
        headers = {
            'User-Agent': 'ServiceResto/1.0'  # Nom et version de l'application
        }
        
        response = requests.get(url, params=params, headers=headers)
        response.raise_for_status()  # Vérifier les erreurs HTTP
        
        # Récupérer les résultats
        results = response.json()
        
        if results:
            # Si des résultats sont trouvés, renvoyer les coordonnées
            latitude = results[0]['lat']
            longitude = results[0]['lon']
            return jsonify({
                "latitude": latitude,
                "longitude": longitude
            }), 200
        else:
            # Si aucun résultat n'est trouvé
            return jsonify({"error": "Quartier introuvable."}), 404

    except requests.exceptions.RequestException as e:
        return jsonify({"error": f"Erreur lors de la récupération des coordonnées : {str(e)}"}), 500

conn = psycopg2.connect("dbname=prosper user=prosper password=prosper host=localhost")
cursor = conn.cursor(cursor_factory=RealDictCursor)

from uuid import UUID

# Route pour la creation d'un code qr
@app.route('/create_order', methods=['POST'])
def create_order():
    '''
        La fonction recupere les donnees necessaire pour la creation du code qr
        - id du client
        - id du prestataire
        - montant de la commande
    '''
    try :
        clientID = request.json['clientID']
        prestataireID = request.json['prestataireID']
        amount = request.json['amount']

        if not clientID or not prestataireID:
            return jsonify({"error":"donnees erronees"}),400

        try:
            UUID(clientID)  # Vérifie que clientID est un UUID valide
            UUID(prestataireID)  # Vérifie que prestataireID est un UUID valide
        except ValueError:
            return jsonify({"error": "Les IDs doivent être des UUID valides."}), 400


        # Obtenir la date et l'heure actuelles
        current_datetime = datetime.now()

        qr_data = f"Client: {clientID}, Prestataire:{prestataireID}, Amount: {amount}, date:{current_datetime}"
        
        # Génération du QR code
        qr = qrcode.make(qr_data)
        img_io = BytesIO()
        qr.save(img_io, 'PNG')
        img_io.seek(0)
        qr_b64 = base64.b64encode(img_io.getvalue()).decode('utf-8')

        # Enregistrer dans la base de données
        cursor.execute(
            "INSERT INTO orders (clientID, prestataireID, amount, qr_code) VALUES (%s, %s, %s, %s)",
            (clientID, prestataireID, amount, qr_b64)
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
    Elle recupere l'id du client et affiche les codes qr associer aux commandes en cours 
'''
@app.route('/show_qr', methods=['POST'])
def show_qr():
    try:
        user_id = request.json['clientID']
        cursor.execute("SELECT qr_code FROM orders WHERE clientID = %s and etat = 'en cours'", (user_id,))
        qr_codes = cursor.fetchall()
        return jsonify({
            "qr_codes":qr_codes
        }),200
    except requests.exceptions.RequestException as e:
        return jsonify({"error": f"Erreur lors de la récupération des coordonnées : {str(e)}"}), 500


'''
    Affichage du code avec interface web
'''
@app.route('/show', methods=['GET', 'POST'])
def show():
    if request.method == 'POST':
        user_id = request.form['clientid']
        cursor.execute("SELECT * FROM orders WHERE clientid = %s", (user_id,))
        orders = cursor.fetchall()
        return render_template('show_qr.html', orders=orders)
    return render_template('affiche_qr.html')


'''
    Verification d'un code qr
'''

@app.route('/scan_qr', methods=['POST', 'GET'])
def scan_qr():
    # Si la méthode est POST, traiter la donnée du QR code
    if request.method == 'POST':
        try:
            # Essayer de récupérer les données envoyées en JSON
            data = request.get_json()
            qr_code = data.get('qr_code')

            if qr_code:
                # Si un QR code est trouvé, on le renvoie dans la réponse JSON
                return jsonify({"qr_code": qr_code}), 200
            else:
                # Si aucun QR code n'est détecté, renvoyer une erreur
                return jsonify({"qr_code": None, "message": "Aucun QR code détecté."}), 404
        except Exception as e:
            # En cas d'erreur interne, renvoyer un message d'erreur avec le code 500
            return jsonify({"error": str(e)}), 500
    else:
        # Si la méthode est GET, afficher une page HTML pour vérifier le QR code
        return render_template('verifier_qr.html')

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
            UUID(prestataireId)  # Vérifie que prestataireID est un UUID valide
        except ValueError:
            return jsonify({"error": "L'ID doit etre un UUID valide."}), 400

        if prestataireId:
            cursor.execute("SELECT * FROM orders WHERE qr_code = %s and etat = 'en cours'", (qr_code,)) # on recupere le code associer au qr
            orders = cursor.fetchone()
            if orders:
                if orders['prestataireid'] == prestataireId: # On verifie si le prestataire est le bon
                    cursor.execute("UPDATE orders SET etat = 'validé' WHERE id = %s", (orders['id'],))
                    conn.commit()
                    return jsonify(
                        {
                            "message": "Commande valide avec succès",
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

'''
    Definir notre application comme client eureka
'''

# from eureka import Eureka
#
# eureka_client = Eureka(
#     app_name='ServiceResto-flask-app',
#     eureka_server='http://localhost:8761/eureka',
#     instance_port=5000,
#     instance_ip='127.0.0.1'
# )
# eureka_client.start()


if __name__ == '__main__':
    app.run(host='0.0.0.0',debug=True,port=8761)
