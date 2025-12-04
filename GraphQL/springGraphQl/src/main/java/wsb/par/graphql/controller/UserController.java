package wsb.par.graphql.controller;



import wsb.par.graphql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import wsb.par.graphql.repository.UserRepository;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Ta metoda zostanie "podpięta" pod pole 'users' w typie 'Query' w schemacie.
    // Nazwa metody musi pasować do nazwy pola.
    @QueryMapping
    public List<User> users() {
        System.out.println(">>> Wywołano query 'users'");
        return userRepository.findAll();
    }

    // Ta metoda zostanie "podpięta" pod pole 'createUser' w typie 'Mutation'.
    // Adnotacja @Argument mapuje argumenty ze schematu na parametry metody.
    @MutationMapping
    public User createUser(@Argument String name, @Argument String email) {
        System.out.println(">>> Wywołano mutację 'createUser' z danymi: " + name + ", " + email);
        User newUser = new User(name, email);
        return userRepository.save(newUser);
    }
}
