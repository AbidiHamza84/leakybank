package fr.ing.secu.leakybank.domain.user.imports;

import java.util.List;
import java.util.Optional;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.domain.user.User;

@DDD.Repository
public interface UserRepository {
    Optional<Customer> authenticate(User user);
    Optional<Customer> findByLogin(String login);
    List<Customer> getAllCustomers();
}
