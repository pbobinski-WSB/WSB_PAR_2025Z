package wsb.par.graphql.publisher;


import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import wsb.par.graphql.model.User;

@Component
public class UserEventPublisher {

    // Sinks.Many to reaktywny "emiter", do którego możemy "pchać" zdarzenia
    private final Sinks.Many<User> sink = Sinks.many().multicast().onBackpressureBuffer();

    // Metoda do publikowania nowego użytkownika
    public void publish(User user) {
        sink.tryEmitNext(user);
    }

    // Metoda, która zwraca strumień zdarzeń do nasłuchiwania
    public Flux<User> getUserStream() {
        return sink.asFlux();
    }
}
