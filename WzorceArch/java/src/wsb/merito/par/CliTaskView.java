package wsb.merito.par;

import java.util.List;

/**
 * Nowy, uproszczony Widok.
 * Jego jedynym zadaniem jest wyświetlanie wyników w konsoli.
 * Nie ma w nim żadnej logiki związanej z interaktywnym menu czy pobieraniem danych od użytkownika.
 */
public class CliTaskView {

    public void displayTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Brak zadań na liście.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String errorMessage) {
        System.err.println("Błąd: " + errorMessage);
    }
}
