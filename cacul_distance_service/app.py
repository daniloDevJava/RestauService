from flask import Flask, request, jsonify
from py_eureka_client.eureka_client import EurekaClient
import math
from eureka_config import *

from ..settings import EUREKA_SERVER

app_distance = Flask(__name__)

client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)
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
@app_distance.route('/calcul-distance', methods=['POST'])
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

if __name__ == "__main__":
    # Enregistrer le service auprès d'Eureka
    client.start()

    try:
        app_distance.run(host="0.0.0.0", port=EUREKA_PORT,debug=True)
    finally:
        # Désenregistrer le service lors de l'arrêt de l'application
        client.stop()
