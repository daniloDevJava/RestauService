from flask import Flask, request, jsonify,redirect, url_for, render_template
import qrcode
from io import BytesIO
import base64
import psycopg2
from psycopg2.extras import RealDictCursor
import math,requests
from datetime import datetime

app = Flask(__name__)

# Fonction pour calculer la distance entre deux points géographiques
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
            'User-Agent': 'ServiceResto/1.0'  # Remplacez par un nom d'application approprié
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


@app.route('/create_order', methods=['GET', 'POST'])
def create_order():
    if request.method == 'POST':
        clientID = request.form['clientID']
        prestataireID = request.form['prestataireID']
        amount = request.form['amount']

        if not clientID or not prestataireID:
            return jsonify({"error":"donnees errone"}),400

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

        return jsonify({
            "code":qr_b64
            }),200

    return render_template('create_order.html')


@app.route('/show_qr', methods=['GET', 'POST'])
def show_qr():
    if request.method == 'POST':
        user_id = request.form['ClientID']
        cursor.execute("SELECT * FROM orders WHERE clientID = %s", (user_id,))
        orders = cursor.fetchall()
        return render_template('show_qr.html', orders=orders)
    return render_template('affiche_qr.html')


if __name__ == '__main__':
    app.run(host='192.168.123.91',debug=True,port=5000)
