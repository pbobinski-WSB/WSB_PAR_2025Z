package clients;


import springws.CalculatorPortType;
import springws.CalculatorService;

public class CalculatorClientSimple {
    public static void main(String[] args) {
        // 1. Stwórz instancję wygenerowanej klasy serwisu.
        //    Ona sama wie, gdzie szukać WSDL (informacja jest w adnotacjach wygenerowanego kodu).
        CalculatorService serviceFactory = new CalculatorService();

        // 2. Pobierz port za pomocą dedykowanej, wygenerowanej metody.
        //    Nazwa tej metody zazwyczaj to "get" + nazwa portu z WSDL (np. "getCalculatorPort").
        //    To jest NAJPROSTSZY sposób.
        CalculatorPortType port = serviceFactory.getCalculatorPort();

        // --- Wywołujemy metody biznesowe (ta część jest identyczna) ---

        System.out.println("Wywołuję metodę 'add(100, 23)'...");
        int sumResult = port.add(100, 23);
        System.out.println("Wynik dodawania: " + sumResult);

        System.out.println("\nWywołuję metodę 'hello(\"Entuzjasta SOAP\")'...");
        String helloResult = port.hello("Entuzjasta SOAP");
        System.out.println("Wynik powitania: " + helloResult);
    }
}