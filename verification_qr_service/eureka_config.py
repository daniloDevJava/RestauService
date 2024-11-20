import psycopg2
from psycopg2.extras import RealDictCursor

# Configuration du client Eureka
EUREKA_SERVER = "http://37.60.244.227:8761/eureka"
EUREKA_APP_NAME = "show_qr-service"
EUREKA_PORT = 5005
app_name = 'ServiceResto/1.0'

conn = psycopg2.connect("dbname=prosper user=prosper password=prosper host=localhost")

cursor = conn.cursor(cursor_factory=RealDictCursor)