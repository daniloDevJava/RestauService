from eureka_config import *
from py_eureka_client.eureka_client import EurekaClient
from flask import Flask,request,jsonify
from settings import *

app_suggestion_nom = Flask(__name__)

client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)

@app_suggestion_nom.route('/suggestion',methods=['POST'])
def suggestionNom():
    data = request.get_json()
    nom = data.get('nom')
    if not nom:
        return jsonify({
            "noms": None
        })
    # Ajoutez le caractère générique pour la recherche
    cursor.execute("SELECT distinct nom FROM nourritures WHERE nom LIKE %s", (f'{nom}%',))
    orders = cursor.fetchall()

    # Transformez les résultats en une liste de noms
    noms = [order['nom'] for order in orders]

    if noms:
        return jsonify({"noms": noms})
    else:
        if len(nom) > 5:
            # Insertion du nom s'il n'existe pas
            try:
                cursor.execute("INSERT INTO nourritures(nom) VALUES (%s)", (nom,))
                conn.commit()
                return jsonify({"noms": None})
            except Exception as e:
                return jsonify({"error": str(e)}), 500
        else:
            return jsonify({"noms": None})

import asyncio

async def start_eureka_client():
    await client.start()

if __name__ == "__main__":
    # Enregistrer le service auprès d'Eureka
    loop = asyncio.get_event_loop()
    loop.run_until_complete(start_eureka_client())

    try:
        app_suggestion_nom.run(host="0.0.0.0", port=EUREKA_PORT, debug=True)
    finally:
        # Désenregistrer le service lors de l'arrêt de l'application
        loop.run_until_complete(client.stop())