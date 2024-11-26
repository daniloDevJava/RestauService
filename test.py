from flask import Flask,render_template
from flask_cors import CORS

app = Flask(__name__)

CORS(app, resources={r"/*": {"origins": "*"}}, supports_credentials=True)

@app.route('/')
def index():
    return render_template('/test_position.html')


if __name__ == '__main__':
    app.run(debug=True,port=5000)