package fr.ing.secu.leakybank.pages.admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.ing.secu.leakybank.UserSession;
import fr.ing.secu.leakybank.infrastructure.user.repository.db.UsersDAO;
import fr.ing.secu.leakybank.pages.BaseController;
import fr.ing.secu.leakybank.pages.admin.sql.SQLConsoleForm;

/**
 * MVC controller for admin features
 * 
 * @author chouippea
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	
	private final UsersDAO usersDao;
	
	private final JdbcTemplate jdbcTemplate;
	
	private final UserSession userSession;

	public AdminController(UsersDAO usersDao, JdbcTemplate jdbcTemplate, UserSession userSession) {
		this.usersDao = usersDao;
		this.jdbcTemplate = jdbcTemplate;
		this.userSession = userSession;
	}

	/**
	 * Main admin page
	 */
	@RequestMapping(value="/users", method = RequestMethod.GET)
	public String mainPage(Model model) throws Exception {
		if (!userSession.getUser().isAdmin()) {
			throw new Exception("Action not allowed");
		}

		model.addAttribute("users", usersDao.findUsers());
		return "admin/users";
	}
	
	@RequestMapping(value="/users/{userLogin}/delete", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("userLogin") String userLogin, Model model) throws Exception {
		if (!userSession.getUser().isAdmin()) {
			throw new Exception("Action not allowed");
		}
		usersDao.deleteUser(userLogin);
		mainPage(model);
		
		return "admin/users :: usersTable";
	}

	@RequestMapping(method = RequestMethod.GET, value="/sqlConsole")
	public String displaySQLConsole(@ModelAttribute("sqlForm") SQLConsoleForm sqlForm, BindingResult result, final ModelMap model) throws Exception {
		if (!userSession.getUser().isAdmin()) {
			throw new Exception("Action not allowed");
		}

		return "admin/sql";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/sqlConsole")
	public String executeSQLQuery(@ModelAttribute("sqlForm") @Valid SQLConsoleForm sqlForm, BindingResult result, final ModelMap model) throws Exception {
		if (!userSession.getUser().isAdmin()) {
			throw new Exception("Action not allowed");
		}

		try {
		SQLConsoleForm queryResult = jdbcTemplate.execute(sqlForm.getSqlQuery(), (PreparedStatement ps) -> {

			SQLConsoleForm data = new SQLConsoleForm();
			data.setColumnNames(new ArrayList<>());
			data.setResultSet(new ArrayList<>());
			ps.execute();
			ResultSet rs = ps.getResultSet();

			if (rs != null) {
				// Get the list of column names
				ResultSetMetaData metaData = rs.getMetaData();
				for (int i = 0; i < metaData.getColumnCount(); i++) {
					data.getColumnNames().add(metaData.getColumnLabel(i + 1 ));
				}
				while(rs.next()) {
					ArrayList<String> row = new ArrayList<String>();
					for (int i = 0; i < metaData.getColumnCount(); i++) {
						row.add(rs.getString(i + 1));
					}
					data.getResultSet().add(row);
				}

				rs.close();
			}

			return data;
		});


		sqlForm.setColumnNames(queryResult.getColumnNames());
		sqlForm.setResultSet(queryResult.getResultSet());

		} catch (DataAccessException ex) {
			result.rejectValue("sqlQuery", "sqlQuery.badRequest", ex.getMostSpecificCause().getMessage());
		}
		
		return "admin/sql";
	}
	

}
