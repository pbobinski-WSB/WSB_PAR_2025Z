package wsb.merito.par;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks); // Zwróć kopię, aby zapobiec modyfikacji z zewnątrz
    }

    public void addTask(String description) {
        tasks.add(new Task(nextId++, description));
    }

    public Optional<Task> findTaskById(int id) {
        return tasks.stream().filter(task -> task.getId() == id).findFirst();
    }
}