package wsb.merito.par;

import java.util.List;
import java.util.Scanner;

public class TaskView {
    private final Scanner scanner = new Scanner(System.in);

    public void displayTasks(List<Task> tasks) {
        System.out.println("\n--- Twoje Zadania ---");
        if (tasks.isEmpty()) {
            System.out.println("Brak zadań na liście.");
        } else {
            tasks.forEach(System.out::println);
        }
        System.out.println("--------------------");
    }

    public int getMenuChoice() {
        System.out.println("\nMenu:");
        System.out.println("1. Wyświetl wszystkie zadania");
        System.out.println("2. Dodaj nowe zadanie");
        System.out.println("3. Oznacz zadanie jako wykonane");
        System.out.println("0. Wyjdź");
        System.out.print("Wybierz opcję: ");
        return scanner.nextInt();
    }

    public String getNewTaskDescription() {
        scanner.nextLine(); // Konsumuj znak nowej linii
        System.out.print("Wpisz treść nowego zadania: ");
        return scanner.nextLine();
    }

    public int getTaskIdToMarkAsDone() {
        System.out.print("Podaj ID zadania do oznaczenia jako wykonane: ");
        return scanner.nextInt();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
