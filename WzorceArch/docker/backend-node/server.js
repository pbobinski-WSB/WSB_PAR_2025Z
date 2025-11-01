const express = require('express');
const cors = require('cors');
const { Pool } = require('pg');

const app = express();
app.use(cors()); // Umożliwia komunikację z frontendem
app.use(express.json()); // Parser dla ciała żądań w formacie JSON

// Konfiguracja połączenia z bazą danych PostgreSQL z docker-compose
const pool = new Pool({
  connectionString: process.env.DATABASE_URL,
});

// Inicjalizacja tabeli, jeśli nie istnieje
const initDb = async () => {
  const query = `
    CREATE TABLE IF NOT EXISTS task (
      id SERIAL PRIMARY KEY,
      description VARCHAR(200) NOT NULL,
      is_done BOOLEAN DEFAULT FALSE
    );
  `;
  await pool.query(query);
  console.log("Tabela 'task' jest gotowa.");
};

// --- Kontroler (obsługuje żądania HTTP) ---

// GET /api/tasks - Pobierz wszystkie zadania
app.get('/api/tasks', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM task ORDER BY id');
    // Zmieniamy klucz is_done na isDone dla zgodności z kontraktem API
    const tasks = result.rows.map(task => ({
      id: task.id,
      description: task.description,
      isDone: task.is_done 
    }));
    res.json(tasks);
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
});

// POST /api/tasks - Dodaj nowe zadanie
app.post('/api/tasks', async (req, res) => {
  try {
    const { description } = req.body;
    const result = await pool.query(
      'INSERT INTO task (description) VALUES ($1) RETURNING *',
      [description]
    );
    const newTask = result.rows[0];
    res.status(201).json({
        id: newTask.id,
        description: newTask.description,
        isDone: newTask.is_done
    });
  } catch (err) {
    console.error(err);
    res.status(500).send('Server Error');
  }
});

// PUT /api/tasks/:id - Zaktualizuj zadanie
app.put('/api/tasks/:id', async (req, res) => {
    try {
        const { id } = req.params;
        const { isDone } = req.body;
        const result = await pool.query(
            'UPDATE task SET is_done = $1 WHERE id = $2 RETURNING *',
            [isDone, id]
        );
        const updatedTask = result.rows[0];
        res.json({
            id: updatedTask.id,
            description: updatedTask.description,
            isDone: updatedTask.is_done
        });
    } catch (err) {
        console.error(err);
        res.status(500).send('Server Error');
    }
});


const PORT = 5000;
app.listen(PORT, () => {
  console.log(`Serwer Node.js działa na porcie ${PORT}`);
  initDb();
});