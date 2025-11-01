package wsb.merito.par;

public class CliMain {
    public static void main(String[] args) {
        // 1. Używamy TEGO SAMEGO modelu co w poprzedniej aplikacji!
        TaskRepository repository = new TaskRepository();

        // Wypełniamy go przykładowymi danymi, aby polecenie 'list' coś pokazało
        repository.addTask("Przygotować materiały na ćwiczenia");
        repository.addTask("Sprawdzić projekty studentów");

        // 2. Tworzymy instancje NOWYCH komponentów Widoku i Kontrolera
        CliTaskView view = new CliTaskView();
        CliTaskController controller = new CliTaskController(repository, view);

        // 3. Uruchamiamy logikę, przekazując argumenty z linii poleceń
        controller.execute(args);
    }
}
