package wsb.par.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

/**
 * To jest nasz KONTRAKT (interfejs).
 * Definiuje on, jakie operacje udostępnia nasz serwis, ale nie zawiera ich implementacji.
 * Adnotacja @WebService tutaj definiuje publiczny kontrakt usługi.
 */
@WebService(
        name = "CalculatorPortType", // Nazwa techniczna w WSDL
        targetNamespace = "http://ws.par.wsb/" // Unikalna przestrzeń nazw dla naszego serwisu
)
public interface ITestService {

    /**
     * Operacja zwracająca powitanie.
     * @param name imię do powitania.
     * @return spersonalizowane powitanie.
     */
    @WebMethod // Ta adnotacja jest opcjonalna w interfejsie, ale poprawia czytelność
    String hello(@WebParam(name = "name") String name);

    /**
     * Operacja dodająca dwie liczby.
     * @param a pierwsza liczba.
     * @param b druga liczba.
     * @return suma liczb.
     */
    @WebMethod
    int add(@WebParam(name = "number1") int a, @WebParam(name = "number2") int b);
}