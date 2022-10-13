package fr.ing.secu.leakybank.infrastructure.administration.repository;

import fr.ing.secu.leakybank.domain.administration.AdminRepository;
import fr.ing.secu.leakybank.domain.administration.SqlQuery;
import fr.ing.secu.leakybank.domain.administration.SqlResult;
import fr.ing.secu.leakybank.domain.user.Customer;
import fr.ing.secu.leakybank.infrastructure.user.repository.db.UsersDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AdminRepositoryImpl implements AdminRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UsersDAO usersDAO;

    public AdminRepositoryImpl(JdbcTemplate jdbcTemplate, UsersDAO usersDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.usersDAO = usersDAO;
    }

    @Override
    public Optional<SqlResult> executeQuery(SqlQuery query) {
        return Optional.ofNullable(
                jdbcTemplate.execute(query.getSqlQuery(), (PreparedStatement ps) -> {
                            ps.execute();
                            ResultSet rs = ps.getResultSet();

                            if (rs == null) {
                                return null;
                            }

                            SqlResult result = new SqlResult();

                            // Get metadata
                            ResultSetMetaData metaData = rs.getMetaData();

                            // Get columns names
                            for (int i = 0; i < metaData.getColumnCount(); i++) {
                                result.getColumnsNames().add(metaData.getColumnLabel(i + 1));
                            }

                            // Collect result from returned rows
                            while (rs.next()) {
                                ArrayList<String> row = new ArrayList<>();
                                for (int i = 0; i < metaData.getColumnCount(); i++) {
                                    row.add(rs.getString(i + 1));
                                }
                                result.getResultSet().add(row);
                            }

                            rs.close();

                            return result;
                        }
                )
        );
    }

    @Override
    public void deleteCustomer(Customer customer) {
        usersDAO.deleteCustomer(customer);
    }
}
