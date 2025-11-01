package wsb.merito.par;

/**
 * Nowy Kontroler, który operuje na argumentach linii poleceń.
 * Nie ma pętli while(true), przetwarza polecenie i kończy działanie.
 */
public class CliTaskController {
    private final TaskRepository model;
    private final CliTaskView view;

    public CliTaskController(TaskRepository model, CliTaskView view) {
        this.model = model;
        this.view = view;
    }

    // Metoda uruchamiająca logikę na podstawie argumentów z linii poleceń
    public void execute(String[] args) {
        if (args.length == 0) {
            view.showError("Brak polecenia. Dostępne polecenia: list, add, done.");
            return;
        }

        String command = args[0];

        switch (command) {
            case "list" -> showAllTasks();
            case "add" -> {
                if (args.length < 2) {
                    view.showError("Brak opisu zadania. Użycie: add \"opis zadania\"");
                } else {
                    addNewTask(args[1]);
                }
            }
            case "done" -> {
                if (args.length < 2) {
                    view.showError("Brak ID zadania. Użycie: done <id>");
                } else {
                    try {
                        markTaskAsDone(Integer.parseInt(args[1]));
                    } catch (NumberFormatException e) {
                        view.showError("ID zadania musi być liczbą.");
                    }
                }
            }
            default -> view.showError("Nieznane polecenie: " + command);
        }
    }

    private void showAllTasks() {
        var tasks = model.getAllTasks();
        view.displayTasks(tasks);
    }

    private void addNewTask(String description) {
        model.addTask(description);
        view.showMessage("Zadanie \"" + description + "\" dodane pomyślnie.");
    }

    private void markTaskAsDone(int id) {
        model.findTaskById(id).ifPresentOrElse(
                task -> {
                    task.setDone(true);
                    view.showMessage("Zadanie " + id + " oznaczone jako wykonane.");
                },
                () -> view.showError("Nie znaleziono zadania o ID: " + id)
        );
    }
}
