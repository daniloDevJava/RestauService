from flask import Flask, request, jsonify
from py_eureka_client.eureka_client import EurekaClient
import requests
from eureka_config import *

app_coordonnees = Flask(__name__)

client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)

# Route pour afficher les coordonnes geographique d'un quartier par son nom en utilisant l'api Nominatim
@app_coordonnees.route('/quartier-coordonnees', methods=['POST'])
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

if __name__ == "__main__":
    # Enregistrer le service auprès d'Eureka
    client.start()

    try:
        app_coordonnees.run(host="0.0.0.0", port=EUREKA_PORT,debug=True)
    finally:
        # Désenregistrer le service lors de l'arrêt de l'application
        client.stop()