from flask import Flask

app = Flask(__name__)

@app.route("/api")
def home():
    return ''

@app.route("/api/hello-world")
def hello():
    return "Hello, World!"


if __name__ == "__main__":
    app.run(debug=True, host="localhost", port="9090")