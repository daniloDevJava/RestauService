from flask import Flask, request, jsonify
from py_eureka_client.eureka_client import EurekaClient
import requests
from eureka_config import *

from settings import *

from flask_cors import CORS

app = Flask(__name__)

from flask_cors import CORS

CORS(app, resources={r"/*": {"origins": "*"}}, supports_credentials=True)

client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)

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
            'User-Agent': app_name  # Nom et version de l'application
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

'''
    Fonction qui pour les coordonnees geographique d'un point ressort le nom du quatier
'''
@app.route('/get_location', methods=['POST'])
def get_location_info():

    data = request.get_json()
    lat = data.get('latitude')
    lon = data.get('longitude')

    if lat is None or lon is None:
        return jsonify({"message": "Latitude et longitude sont obligatoires"}), 400

    try:
        lat = float(lat)
        lon = float(lon)
    except ValueError:
        return jsonify({"message": "Latitude et longitude doivent être des nombres"}), 400

    url = "https://nominatim.openstreetmap.org/reverse"
    params = {'lat': lat, 'lon': lon, 'format': 'json'}
    headers = {"User-Agent": app_name}

    try:
        response = requests.get(url, params=params, headers=headers, timeout=10)
        response.raise_for_status()
        data = response.json()

        ville = data.get('address', {}).get('city')
        quartier = (data.get('address', {}).get('neighbourhood') or
            data.get('address', {}).get('suburb') or
            data.get('address', {}).get('village') or
            data.get('address', {}).get('hamlet'))

        return jsonify({"ville": ville, "quartier": quartier}), 200
    except requests.RequestException as e:
        return jsonify({"message": "Erreur lors de la communication avec l'API", "error": str(e)}), 500
    except Exception as e:
        return jsonify({"message": "Erreur inattendue", "error": str(e)}), 500

# Fonction pour calculer la distance entre deux points géographiques
'''
    Elle prend en parametre les longitudes et latitudes des personnes
'''
import math

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
        lat1 = float(data['lat1'])
        lon1 = float(data['lon1'])
        lat2 = float(data['lat2'])
        lon2 = float(data['lon2'])

        # Calculer la distance
        distance = calculate_distance(lat1, lon1, lat2, lon2)
        
        # Retourne la distance en réponse JSON
        return jsonify({'distance_km': distance}), 200
    except KeyError:
        return jsonify({'error': 'Invalid input. Make sure lat1, lon1, lat2, and lon2 are provided.'}), 400


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