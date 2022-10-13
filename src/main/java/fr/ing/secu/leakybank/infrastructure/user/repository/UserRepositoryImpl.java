package fr.ing.secu.leakybank.infrastructure.user.repository;

import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.domain.user.User;
import fr.ing.secu.leakybank.domain.user.imports.UserRepository;
import fr.ing.secu.leakybank.infrastructure.user.mapper.UserMapper;
import fr.ing.secu.leakybank.infrastructure.user.repository.db.UsersDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserRepositoryImpl implements UserRepository {

    final
    UsersDAO userDao;

    public UserRepositoryImpl(UsersDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<Customer> authenticate(User user) {
        return userDao.login(user.getLogin(), user.getPassword())
                .map(UserMapper.INSTANCE::toDomain);
    }

    @Override
    public Optional<Customer> findByLogin(String login) {
        return userDao.findUser(login).map(UserMapper.INSTANCE::toDomain);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return userDao.findUsers().stream()
                .map(UserMapper.INSTANCE::toDomain)
                .collect(Collectors.toList());
    }
}
