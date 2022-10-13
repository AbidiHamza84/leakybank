package fr.ing.secu.leakybank.domain;

import fr.ing.secu.leakybank.domain.administration.SqlQuery;
import fr.ing.secu.leakybank.domain.administration.SqlResult;
import fr.ing.secu.leakybank.domain.user.Customer;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

import java.util.Optional;

@DDD.Service
public interface AdminService {
    Optional<SqlResult> executeQuery(SqlQuery query);
    void deleteUser(Customer customer);
}
