from flask import Flask, request, jsonify, render_template
from sqlalchemy import create_engine, text

app = Flask(__name__)

# Connexion à la base de données PostgreSQL
engine = create_engine('postgresql://user_bd:USER_BD@localhost/user_bd')


# calcule de la distance entre deux points sur la carte
import math

def calculate_distance(lat1, lon1, lat2, lon2): # lat est mis pour lattitude et lon est pour longitude
    # Formule de Haversine pour calculer la distance en kilomètres
    R = 6371  # Rayon de la Terre en kilomètres
    dlat = math.radians(lat2 - lat1)
    dlon = math.radians(lon2 - lon1)
    a = (math.sin(dlat / 2) ** 2 +
         math.cos(math.radians(lat1)) * math.cos(math.radians(lat2)) *
         math.sin(dlon / 2) ** 2)
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return R * c  # Retourne la distance en kilomètres


# on fais l'appel pour avoir la page ou sera afficher les plus proches restaurants
@app.route('/')
def index():
    return render_template('nearby_restaurant.html')


# On calcules la distance de chaque restaurant au client

@app.route('/nearby-restaurants', methods=['POST'])
def nearby_restaurants():
    data = request.json
    latitude = data['latitude']
    longitude = data['longitude']

    try:
        # Requête SQL pour récupérer tous les restaurants
        with engine.connect() as conn:
            query = text("""
                SELECT name, 
                    ST_Y(ST_SetSRID(location::geometry, 4326)) AS lat, 
                    ST_X(ST_SetSRID(location::geometry, 4326)) AS lon  -- Récupérer latitude et longitude
                FROM restaurants
            """)

            # Exécuter la requête
            result = conn.execute(query)

            # Calculer les distances
            restaurants = []
            for row in result:
                dist = calculate_distance(latitude, longitude, row[1], row[2])  # Utiliser les index
                restaurants.append({
                    'name': row[0],  # Nom du restaurant
                    'distance': dist
                })

        # Trier par distance et garder les 10 plus proches si necessaire
        restaurants = sorted(restaurants, key=lambda x: x['distance'])[:10]

        return jsonify({'restaurants': restaurants}), 200
    except Exception as e:
        # Afficher l'erreur dans les logs
        print(f"Erreur lors de la récupération des restaurants: {str(e)}")
        return jsonify({'error': 'Une erreur s\'est produite lors de la récupération des restaurants.'}), 500


# test d'ajout pour verifier le systene de geolocalisation
@app.route('/save-location', methods=['POST'])
def save_location():
    data = request.json
    latitude = data['latitude']
    longitude = data['longitude']

    # Insérer la position dans la base de données PostgreSQL
    with engine.connect() as conn:
        query = text("""
            INSERT INTO user_locations (location)
            VALUES (ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326))
        """)
        conn.execute(query, {'longitude': longitude , 'latitude': latitude})
        conn.commit()
    return jsonify({'status': 'Location saved successfully'}), 200

if __name__ == '__main__':
    app.run(debug=True)
