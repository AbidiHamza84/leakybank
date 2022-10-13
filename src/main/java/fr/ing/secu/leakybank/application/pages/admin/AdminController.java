package fr.ing.secu.leakybank.application.pages.admin;

import java.util.Optional;

import javax.validation.Valid;

import fr.ing.secu.leakybank.application.pages.admin.mapper.AdminMapper;
import fr.ing.secu.leakybank.domain.AdminService;
import fr.ing.secu.leakybank.domain.UserService;
import fr.ing.secu.leakybank.domain.administration.SqlQuery;
import fr.ing.secu.leakybank.domain.administration.SqlResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.ing.secu.leakybank.UserSession;
import fr.ing.secu.leakybank.pages.BaseController;

/**
 * MVC controller for admin features
 * 
 * @author chouippea
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	private final AdminService adminService;
	private final UserService userService;
	
	private final UserSession userSession;

	public AdminController(AdminService adminService, UserService userService, UserSession userSession) {
		this.adminService = adminService;
		this.userService = userService;
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

		model.addAttribute("users", userService.getAllCustomers());
		return "admin/users";
	}
	
	@RequestMapping(value="/users/{userLogin}/delete", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable("userLogin") String userLogin, Model model) throws Exception {

		if (!userSession.getUser().isAdmin()) {
			throw new Exception("Action not allowed");
		}

		userService.findCustomer(userLogin).ifPresent(adminService::deleteUser);

		mainPage(model);
		
		return "admin/users :: usersTable";
	}

	@RequestMapping(method = RequestMethod.GET, value="/sqlConsole")
	public String displaySQLConsole(@ModelAttribute("sqlForm") SQLConsoleForm sqlForm) throws Exception {
		if (!userSession.getUser().isAdmin()) {
			throw new Exception("Action not allowed");
		}

		return "admin/sql";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/sqlConsole")
	public String executeSQLQuery(@ModelAttribute("sqlForm") @Valid SQLConsoleForm sqlForm) throws Exception {

		if (!userSession.getUser().isAdmin()) {
			throw new Exception("Action not allowed");
		}

		SqlQuery query = AdminMapper.INSTANCE.toDomain(sqlForm);
		Optional<SqlResult> result = this.adminService.executeQuery(query);

		result.ifPresent(sqlResult -> {
			sqlForm.setColumnNames(sqlResult.getColumnsNames());
			sqlForm.setResultSet(sqlResult.getResultSet());
		});

		// result.rejectValue("sqlQuery", "sqlQuery.badRequest", ex.getMostSpecificCause().getMessage());
		
		return "admin/sql";
	}
	

}
