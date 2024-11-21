import psycopg2
from psycopg2.extras import RealDictCursor


conn = psycopg2.connect("dbname=food_team user=food_team password=food_team5 host=localhost")
# conn = psycopg2.connect("dbname=prosper user=prosper password=prosper host=localhost")

cursor = conn.cursor(cursor_factory=RealDictCursor)

EUREKA_SERVER = "http://37.60.244.227:8761"

app_name = 'ServiceResto/1.0'
