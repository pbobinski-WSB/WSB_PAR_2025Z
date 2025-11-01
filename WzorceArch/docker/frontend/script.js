const apiUrl = 'http://localhost:5000/api/tasks'; // Adres naszego backendu
const taskList = document.getElementById('task-list');
const taskForm = document.getElementById('task-form');
const taskInput = document.getElementById('task-input');

// --- Komunikacja z API (interakcja z warstwą logiki) ---

async function fetchTasks() {
    const response = await fetch(apiUrl);
    const tasks = await response.json();
    renderTasks(tasks);
}

async function addTask(description) {
    await fetch(apiUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ description: description })
    });
    fetchTasks();
}

async function updateTaskStatus(id, isDone) {
    await fetch(`${apiUrl}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ isDone: isDone })
    });
    fetchTasks();
}

// --- Renderowanie widoku (warstwa prezentacji) ---

function renderTasks(tasks) {
    taskList.innerHTML = '';
    tasks.forEach(task => {
        const li = document.createElement('li');
        li.className = 'task-item';
        if (task.isDone) {
            li.classList.add('done');
        }

        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.checked = task.isDone;
        checkbox.addEventListener('change', () => {
            updateTaskStatus(task.id, checkbox.checked);
        });

        const span = document.createElement('span');
        span.textContent = task.description;

        li.appendChild(checkbox);
        li.appendChild(span);
        taskList.appendChild(li);
    });
}

// --- Obsługa zdarzeń użytkownika ---

taskForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const description = taskInput.value;
    if (description) {
        addTask(description);
        taskInput.value = '';
    }
});

// Inicjalizacja aplikacji
fetchTasks();