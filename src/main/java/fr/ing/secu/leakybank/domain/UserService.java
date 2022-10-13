package fr.ing.secu.leakybank.domain;

import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.domain.user.User;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

import java.util.List;
import java.util.Optional;

@DDD.Service
public interface UserService {
    Optional<Customer> authenticate(User user);
    Optional<Customer> findCustomer(String login);

    List<Customer> getAllCustomers();
}
