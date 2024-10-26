from flask import Flask, request, jsonify, render_template
import math,requests

app = Flask(__name__)

# Calcul de la distance entre deux points (formule de Haversine)
def calculate_distance(lat1, lon1, lat2, lon2):
    R = 6371  # Rayon de la Terre en kilomètres
    dlat = math.radians(lat2 - lat1)
    dlon = math.radians(lon2 - lon1)
    a = (math.sin(dlat / 2) ** 2 +
         math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) *
         math.sin(dlon / 2) ** 2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return R * c

# Page d'accueil pour afficher les restaurants les plus proches
@app.route('/', methods=['POST', 'GET'])
def index():
    return render_template('save-location.html')


# Sauvegarder la localisation de l'utilisateur
@app.route('/save-location', methods=['POST'])
def save_location():
    data = request.json
    latitude = data.get('latitude')
    longitude = data.get('longitude')

    # Vérification des données de latitude et longitude
    if latitude is None or longitude is None:
        return jsonify({'error': 'Latitude et longitude sont requises.'}), 400

    return jsonify({'latitude': latitude, 'longitude': longitude}), 200

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
                "nom": nom_quartier,
                "latitude": latitude,
                "longitude": longitude
            }), 200
        else:
            # Si aucun résultat n'est trouvé
            return jsonify({"error": "Quartier introuvable."}), 404

    except requests.exceptions.RequestException as e:
        return jsonify({"error": f"Erreur lors de la récupération des coordonnées : {str(e)}"}), 500

@app.route('/quartier-coordonnees-page')
def quartier_coordonnees_page():
    return render_template('quartier-coordonnees.html')


if __name__ == '__main__':
    app.run(debug=True)
