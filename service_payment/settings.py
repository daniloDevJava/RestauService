# Settings for payment
import os
import psycopg2
from psycopg2.extras import RealDictCursor

conn = psycopg2.connect(
    "dbname=prosper user=prosper password=prosper host=postgres"
)

cursor = conn.cursor(cursor_factory=RealDictCursor)


EUREKA_SERVER = os.getenv('EUREKA_SERVER', 'http://foodgo.smartcloudservices.cloud:8761')
EUREKA_APP_NAME = os.getenv('EUREKA_APP_NAME', 'ServicePayment')
EUREKA_INSTANCE_HOST = os.getenv('EUREKA_INSTANCE_HOST', 'payment')
EUREKA_PORT = int(os.getenv('EUREKA_PORT', 5002))

# Création de la table au démarrage de l'application
def create_orders_table():
    create_table_query = """
    CREATE TABLE IF NOT EXISTS orders (
        id SERIAL PRIMARY KEY,                 -- Identifiant unique pour chaque commande
        prestataireid UUID NOT NULL,           -- Identifiant du prestataire (UUID)
        amount INTEGER NOT NULL,               -- Montant de la commande
        qr_code TEXT NOT NULL,                 -- QR code en base64
        commandeid UUID NOT NULL,              -- Identifiant de la commande associée
        created_at TIMESTAMP DEFAULT NOW()     -- Date et heure de création de l'entrée
    );
    """
    try:
        cursor.execute(create_table_query)
        conn.commit()
        print("Table `orders` créée ou déjà existante.")
    except Exception as e:
        conn.rollback()
        print(f"Erreur lors de la création de la table : {str(e)}")

# Appel de la fonction pour créer la table
create_orders_table()

