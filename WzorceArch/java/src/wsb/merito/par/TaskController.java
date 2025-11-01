package wsb.merito.par;

public class TaskController {
    private final TaskRepository model;
    private final TaskView view;

    public TaskController(TaskRepository model, TaskView view) {
        this.model = model;
        this.view = view;
    }

    public void run() {
        boolean running = true;
        while (running) {
            int choice = view.getMenuChoice();
            switch (choice) {
                case 1 -> showAllTasks();
                case 2 -> addNewTask();
                case 3 -> markTaskAsDone();
                case 0 -> running = false;
                default -> view.showMessage("Nieznana opcja.");
            }
        }
        view.showMessage("Do widzenia!");
    }

    private void showAllTasks() {
        var tasks = model.getAllTasks(); // Pobierz dane z modelu
        view.displayTasks(tasks);      // Przekaż dane do widoku
    }

    private void addNewTask() {
        String description = view.getNewTaskDescription();
        model.addTask(description);
        view.showMessage("Zadanie dodane pomyślnie.");
    }

    private void markTaskAsDone() {
        int id = view.getTaskIdToMarkAsDone();
        model.findTaskById(id).ifPresentOrElse(
                task -> {
                    task.setDone(true);
                    view.showMessage("Zadanie " + id + " oznaczone jako wykonane.");
                },
                () -> view.showMessage("Nie znaleziono zadania o ID: " + id)
        );
    }
}