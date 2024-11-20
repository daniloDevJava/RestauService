from flask import Flask, request, jsonify,render_template
import qrcode
from io import BytesIO
import base64
import psycopg2
from psycopg2.extras import RealDictCursor
import math,requests
from datetime import datetime

app = Flask(__name__)

app_name = 'ServiceResto/1.0'

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

conn = psycopg2.connect("dbname=prosper user=prosper password=prosper host=localhost")
cursor = conn.cursor(cursor_factory=RealDictCursor)

'''
    Fonction qui pour les coordonnees geographique d'un point ressort le nom du quatier
'''
@app.route('/get_location', methods=['POST'])
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
        clientId = request.json['clientId']
        prestataireId = request.json['prestataireId']
        amount = request.json['amount']

        if not clientId or not prestataireId:
            return jsonify({"error":"donnees erronees"}),400

        try:
            UUID(clientId)  # Vérifie que clientId est un UUID valide
            UUID(prestataireId)  # Vérifie que prestataireId est un UUID valide
        except ValueError:
            return jsonify({"error": "Les IDs doivent être des UUID valides."}), 400


        # Obtenir la date et l'heure actuelles
        current_datetime = datetime.now()

        qr_data = f"Client: {clientId}, Prestataire:{prestataireId}, Amount: {amount}, date:{current_datetime}"
        
        # Génération du QR code
        qr = qrcode.make(qr_data)
        img_io = BytesIO()
        qr.save(img_io, 'PNG')
        img_io.seek(0)
        qr_b64 = base64.b64encode(img_io.getvalue()).decode('utf-8')

        # Enregistrer dans la base de données
        cursor.execute(
            "INSERT INTO orders (clientId, prestataireId, amount, qr_code) VALUES (%s, %s, %s, %s)",
            (clientId, prestataireId, amount, qr_b64)
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
        user_id = request.json['clientId']
        cursor.execute("SELECT qr_code FROM orders WHERE clientId = %s and etat = 'en cours'", (user_id,))
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
        user_id = request.form['clientId']
        cursor.execute("SELECT * FROM orders WHERE clientId = %s", (user_id,))
        orders = cursor.fetchall()
        return render_template('show_qr.html', orders=orders)
    return render_template('affiche_qr.html')


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
                if orders['prestataireId'] == prestataireId: # On verifie si le prestataire est le bon
                    cursor.execute("UPDATE orders SET etat = 'validé' WHERE id = %s", (orders['id'],))
                    conn.commit()

                    # J'envois l'argent dans le compte du prestataire

                    SpringURL = "http://37.60.244.227/updateCount"
                    spring_response = requests.post(SpringURL, json={
                                                            "IdPrestataire": prestataireId,
                                                            "montant": orders['amount']
                                                        }
                                                    )
                    return jsonify(
                        {
                            "message": "Commande valide avec succès",
                            "result":spring_response,
                            # "prestataireId":prestataireId,
                            # "amount":orders['amount']
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

if __name__ == '__main__':
    app.run(host='0.0.0.0',debug=True,port=8761)
