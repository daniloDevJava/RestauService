from flask import Flask,request,jsonify
import requests
from py_eureka_client.eureka_client import EurekaClient
from eureka_config import *

app_location = Flask(__name__)

client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)

'''
    Fonction qui pour les coordonnees geographique d'un point ressort le nom du quatier
'''
@app_location.route('/get_location', methods=['POST'])
def get_location_info():
    if request.method != 'POST':
        return jsonify({"message": "Mauvaise méthode utilisée"}), 400

    data = request.get_json()
    lat = data.get('lat')
    lon = data.get('lon')

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

if __name__ == "__main__":
    # Enregistrer le service auprès d'Eureka
    client.start()

    try:
        app_location.run(host="0.0.0.0", port=EUREKA_PORT,debug=True)
    finally:
        # Désenregistrer le service lors de l'arrêt de l'application
        client.stop()