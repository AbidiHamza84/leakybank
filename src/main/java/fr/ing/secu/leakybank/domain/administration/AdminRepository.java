package fr.ing.secu.leakybank.domain.administration;

import fr.ing.secu.leakybank.domain.user.Customer;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD;

import java.util.Optional;

@DDD.Repository
public interface AdminRepository {
    Optional<SqlResult> executeQuery(SqlQuery query);
    void deleteCustomer(Customer customer);
}
