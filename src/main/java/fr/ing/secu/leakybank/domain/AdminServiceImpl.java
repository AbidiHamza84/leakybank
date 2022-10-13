package fr.ing.secu.leakybank.domain;

import fr.ing.secu.leakybank.domain.administration.AdminRepository;
import fr.ing.secu.leakybank.domain.administration.SqlQuery;
import fr.ing.secu.leakybank.domain.administration.SqlResult;
import fr.ing.secu.leakybank.domain.user.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Optional<SqlResult> executeQuery(SqlQuery query) {
        return adminRepository.executeQuery(query);
    }

    @Override
    public void deleteUser(Customer customer) {
        adminRepository.deleteCustomer(customer);
    }
}
