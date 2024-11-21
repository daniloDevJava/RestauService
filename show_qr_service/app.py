from flask import Flask,request,jsonify
import requests
from eureka_config import *
from py_eureka_client.eureka_client import EurekaClient

from settings import *

app_qr_show = Flask(__name__)

client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)

# Route pour afficher les qr des commandes d'un client
'''
    Elle recupere l'id de la commande et affiche le code qr associer en cours 
'''
@app_qr_show.route('/show_qr', methods=['POST'])
def show_qr():
    try:
        commande_id = request.json['commandeId']
        cursor.execute("SELECT qr_code FROM orders WHERE commandeId = %s and etat = 'en cours'", (commande_id,))
        qr_codes = cursor.fetchall()
        return jsonify({
            "qr_codes":qr_codes
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
        app_qr_show.run(host="0.0.0.0", port=EUREKA_PORT, debug=True)
    finally:
        # Désenregistrer le service lors de l'arrêt de l'application
        loop.run_until_complete(client.stop())

