package fr.ing.secu.leakybank.domain;

import org.springframework.stereotype.Service;

import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.domain.user.User;
import fr.ing.secu.leakybank.domain.user.imports.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Customer> authenticate(User user) {
        return userRepository.authenticate(user);
    }

    @Override
    public Optional<Customer> findCustomer(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return userRepository.getAllCustomers();
    }

}
