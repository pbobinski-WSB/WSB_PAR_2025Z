from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_cors import CORS
import os

app = Flask(__name__)
CORS(app) # Umożliwia komunikację z frontendem na innym porcie

# Konfiguracja połączenia z bazą danych PostgreSQL z docker-compose
app.config['SQLALCHEMY_DATABASE_URI'] = os.environ.get('DATABASE_URL')
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

# Model Danych (część wzorca MVC)
class Task(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    description = db.Column(db.String(200), nullable=False)
    is_done = db.Column(db.Boolean, default=False)

    def to_dict(self):
        return {"id": self.id, "description": self.description, "isDone": self.is_done}

# Inicjalizacja bazy danych przy pierwszym uruchomieniu
with app.app_context():
    db.create_all()

# Kontroler (część wzorca MVC) - obsługuje żądania HTTP
@app.route('/api/tasks', methods=['GET'])
def get_tasks():
    tasks = Task.query.order_by(Task.id).all()
    return jsonify([task.to_dict() for task in tasks])

@app.route('/api/tasks', methods=['POST'])
def add_task():
    data = request.get_json()
    new_task = Task(description=data['description'])
    db.session.add(new_task)
    db.session.commit()
    return jsonify(new_task.to_dict()), 201

@app.route('/api/tasks/<int:task_id>', methods=['PUT'])
def update_task(task_id):
    task = Task.query.get_or_404(task_id)
    data = request.get_json()
    task.is_done = data['isDone']
    db.session.commit()
    return jsonify(task.to_dict())

if __name__ == '__main__':
    app.run(debug=True)