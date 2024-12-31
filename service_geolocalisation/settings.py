# Settings for geolocalisation
import os
import psycopg2
from psycopg2.extras import RealDictCursor

try:
	conn = psycopg2.connect(
		"dbname=prosper user=prosper password=prosper host=postgres"
	)
except:
	conn = psycopg2.connect(
		"dbname=prosper user=prosper password=prosper host=localhost"
	)
cursor = conn.cursor(cursor_factory=RealDictCursor)

EUREKA_SERVER = os.getenv('EUREKA_SERVER', 'http://foodgo.smartcloudservices.cloud:8761')
EUREKA_APP_NAME = os.getenv('EUREKA_APP_NAME', 'ServiceGeolocalisation')
EUREKA_INSTANCE_HOST = os.getenv('EUREKA_INSTANCE_HOST', 'geolocalisation')
EUREKA_PORT = int(os.getenv('EUREKA_PORT', 5001))

app_name = 'ServiceResto/1.0'
