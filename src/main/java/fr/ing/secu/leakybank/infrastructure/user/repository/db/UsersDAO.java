package fr.ing.secu.leakybank.infrastructure.user.repository.db;

import java.util.List;
import java.util.Optional;

import fr.ing.secu.leakybank.application.pages.login.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/** Maps USERS table result set to User beans*/
	private final RowMapper<UserDTO> userRowMapper = (rs, rowNum) -> new UserDTO(rs.getString("LOGIN"), rs.getString("FIRST_NAME"), rs.getString("LAST_NAME"), rs.getBoolean("IS_ADMIN"));

	/**
	 * Login a user
	 * 
	 * @return Details on the user if login credentials are valid, else return null
	 *         an exception
	 */
	public Optional<UserDTO> login(String login, String password) {
		try {
			return jdbcTemplate.query(
                    "select LOGIN, FIRST_NAME, LAST_NAME, IS_ADMIN from users where login = ? and password = ?"
                    , preparedStatement -> {
						preparedStatement.setString(1, login);
						preparedStatement.setString(2, password);
					}, userRowMapper).stream().findFirst();
		} catch (EmptyResultDataAccessException ex) {
			return Optional.empty();
		}
	}
	
	/** Return the list of non-admin users */
	public List<UserDTO> findUsers() {
		return jdbcTemplate.query(
				"select LOGIN, FIRST_NAME, LAST_NAME, IS_ADMIN from users where IS_ADMIN = ?"
				, preparedStatement -> preparedStatement.setBoolean(1, false), userRowMapper);
	}

	public Optional<UserDTO> findUser(String username) {
		return jdbcTemplate.query(
				"select LOGIN, FIRST_NAME, LAST_NAME, IS_ADMIN from users where LOGIN = ?"
				, preparedStatement -> {
					preparedStatement.setString(1, username);
				}, userRowMapper).stream().findFirst();
	}
	
	public void deleteUser(String login) {
		jdbcTemplate.update("delete from USERS where login = ?"
				, preparedStatement -> preparedStatement.setString(1, login));
	}
}
