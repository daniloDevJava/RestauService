from flask import Flask,request,jsonify

from uuid import UUID
from eureka_config import *
from py_eureka_client.eureka_client import EurekaClient

app_qr_verification = Flask(__name__)

client = EurekaClient(
    eureka_server=EUREKA_SERVER,
    app_name=EUREKA_APP_NAME,
    instance_port=EUREKA_PORT,
    instance_host="localhost",
)

# Veification de code qr

'''
    Ce service utilise deux donnee envoyer en post:
    - le code_qr(qr_code): qui est le text associer au code_qr scanner
    - l'id du prestataire qui scanne le code(prestataireId)
'''
@app_qr_verification.route('/verification_qr',methods=['POST'])

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
                if orders['prestataireid'] == prestataireId: # On verifie si le prestataire est le bon
                    cursor.execute("UPDATE orders SET etat = 'validé' WHERE id = %s", (orders['id'],))
                    conn.commit()

                    # J'envois l'argent dans le compte du prestataire

                    # SpringURL = "http://37.60.244.227/updateCount"
                    # spring_response = requests.post(SpringURL, json={
                    #                                         "IdPrestataire": prestataireId,
                    #                                         "montant": orders['amount']
                    #                                     }
                    #                                 )
                    return jsonify(
                        {
                            "message": "Commande valide avec succès",
                            # "result":spring_response,
                            "prestataireId":prestataireId,
                            "amount":orders['amount']
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


if __name__ == "__main__":
    client.start()
    try:
        app_qr_verification.run(host="0.0.0.0", port=EUREKA_PORT,debug=True)
    finally:
        # Désenregistrer le service lors de l'arrêt de l'application
        client.stop()

