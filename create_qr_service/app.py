from flask import Flask,jsonify,request
import requests
from datetime import datetime
from io import BytesIO
import base64
import qrcode
from eureka_config import *
from py_eureka_client.eureka_client import EurekaClient


from settings import *

app_qr_creation = Flask(__name__)


client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)

from uuid import UUID

# Route pour la creation d'un code qr
@app_qr_creation.route('/create_order', methods=['POST'])

def create_order():
    '''
        La fonction recupere les donnees necessaire pour la creation du code qr
        - id du prestataire
        - id de la commande
        - montant de la commande
    '''
    try :
        prestataireId = request.json['prestataireId']
        amount = int(request.json['amount'])
        commandeId = request.json['commandeId']

        if not prestataireId or not commandeId:
            return jsonify({"error":"donnees erronees"}),400

        try:
            UUID(prestataireId)  # Vérifie que prestataireId est un UUID valide
            UUID(commandeId)
        except ValueError:
            return jsonify({"error": "Les IDs doivent être des UUID valides."}), 400


        # Obtenir la date et l'heure actuelles
        current_datetime = datetime.now()

        qr_data = f"Prestataire:{prestataireId}, Amount: {amount}, date:{current_datetime}, commande:{commandeId}"
        
        # Génération du QR code
        qr = qrcode.make(qr_data)
        img_io = BytesIO()
        qr.save(img_io, 'PNG')
        img_io.seek(0)
        qr_b64 = base64.b64encode(img_io.getvalue()).decode('utf-8')

        # Enregistrer dans la base de données
        cursor.execute(
            "INSERT INTO orders (prestataireid, amount, qr_code, commandeid) VALUES (%s, %s, %s,%s)",
            (prestataireId, amount, qr_b64,commandeId)
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



import asyncio

async def start_eureka_client():
    await client.start()

if __name__ == "__main__":
    # Enregistrer le service auprès d'Eureka
    loop = asyncio.get_event_loop()
    loop.run_until_complete(start_eureka_client())

    try:
        app_qr_creation.run(host="0.0.0.0", port=EUREKA_PORT, debug=True)
    finally:
        # Désenregistrer le service lors de l'arrêt de l'application
        loop.run_until_complete(client.stop())