package clients;


import springws.CalculatorPortType;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class CalculatorClient {
    public static void main(String[] args) throws Exception {
        // Definicja QName na podstawie danych z WSDL
        QName serviceName = new QName(
                "http://ws.par.wsb/",      // <-- Parametr 1: targetNamespace z WSDL
                "CalculatorService"       // <-- Parametr 2: nazwa serwisu (service name) z WSDL
        );

        // Adres URL do pliku WSDL
        URL wsdlUrl = new URL("http://localhost:8080/services/CalculatorService?wsdl");

        // Tworzymy instancję serwisu. To bardziej jawna wersja tego, co robiło "new ISBNService()".
        Service serviceFactory = Service.create(wsdlUrl, serviceName);

        // Pobieramy port (punkt końcowy), podając jego interfejs.
        // To jest odpowiednik .getPort(...) z Twojego przykładu.
        CalculatorPortType port = serviceFactory.getPort(CalculatorPortType.class);

        // --- Wywołujemy metody biznesowe ---

        System.out.println("Wywołuję metodę 'add(15, 7)'...");
        int sumResult = port.add(15, 7);
        System.out.println("Wynik dodawania: " + sumResult);

        System.out.println("\nWywołuję metodę 'hello(\"Student\")'...");
        String helloResult = port.hello("Student");
        System.out.println("Wynik powitania: " + helloResult);
    }
}
