package wsb.par.graphql.repository;


import wsb.par.graphql.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class UserRepository {
    // Używamy listy bezpiecznej wątkowo, na wszelki wypadek
    private final List<User> users = new CopyOnWriteArrayList<>();

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public User save(User user) {
        users.add(user);
        return user;
    }
}
