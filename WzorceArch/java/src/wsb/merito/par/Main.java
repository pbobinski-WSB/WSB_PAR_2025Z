package wsb.merito.par;

public class Main {
    public static void main(String[] args) {
        // Stworzenie instancji komponentów MVC
        TaskRepository repository = new TaskRepository();
        TaskView view = new TaskView();
        TaskController controller = new TaskController(repository, view);

        // Uruchomienie głównej logiki aplikacji
        controller.run();
    }
}
