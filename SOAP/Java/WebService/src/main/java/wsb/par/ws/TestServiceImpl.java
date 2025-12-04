package wsb.par.ws;

import jakarta.jws.WebService;
import wsb.par.ws.ITestService;

/**
 * To jest nasza IMPLEMENTACJA.
 * Ta klasa implementuje interfejs ITestService i dostarcza logikę dla jego metod.
 * Adnotacja @WebService tutaj jest kluczowa, ponieważ łączy tę implementację z kontraktem.
 */
@WebService(
        // 'endpointInterface' mówi CXF: "Ta klasa jest implementacją tego konkretnego interfejsu".
        endpointInterface = "wsb.par.ws.ITestService",
        // 'serviceName' i 'portName' powinny pasować do tego, co chcemy widzieć w WSDL
        serviceName = "CalculatorService",
        portName = "CalculatorPort"
)
public class TestServiceImpl implements ITestService {

    @Override
    public String hello(String name) {
        System.out.println(">>> Wywołano implementację 'hello' z parametrem: " + name);
        if (name == null || name.trim().isEmpty()) {
            return "Hello, stranger!";
        }
        return "Hello, " + name + "!";
    }

    @Override
    public int add(int a, int b) {
        System.out.println(">>> Wywołano implementację 'add' z parametrami: " + a + ", " + b);
        return a + b;
    }
}