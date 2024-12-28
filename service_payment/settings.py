import psycopg2
from psycopg2.extras import RealDictCursor

conn = psycopg2.connect("dbname=prosper user=prosper password=prosper host=postgres")

cursor = conn.cursor(cursor_factory=RealDictCursor)

EUREKA_SERVER = "http://37.60.244.227:8761"

app_name = 'ServiceResto/1.0'
