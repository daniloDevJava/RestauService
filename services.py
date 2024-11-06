from flask import Flask, request, jsonify
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
        cursor.execute("SELECT qr_code FROM orders WHERE clientID = %s", (user_id,))
        qr_codes = cursor.fetchall()
        return jsonify({
            "qr_codes":qr_codes
        }),200
    except requests.exceptions.RequestException as e:
        return jsonify({"error": f"Erreur lors de la récupération des coordonnées : {str(e)}"}), 500

if __name__ == '__main__':
    app.run(host='192.168.123.91',debug=True,port=5000)
