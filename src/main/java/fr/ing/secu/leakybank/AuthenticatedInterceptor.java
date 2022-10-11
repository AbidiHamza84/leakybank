package fr.ing.secu.leakybank;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Redirect unauthenticated users to the login page
 * 
 * @author chouippea
 *
 */
@Component
public class AuthenticatedInterceptor implements HandlerInterceptor {
	private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticatedInterceptor.class);

	private final UserSession userSession;

	public AuthenticatedInterceptor(UserSession userSession) {
		this.userSession = userSession;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception  {

		if (request.getRequestURI().startsWith("/js") ||
				request.getRequestURI().startsWith("/css") ||
				request.getRequestURI().startsWith("/img")) {
			return true;
		}

		LOGGER.debug("Url: {}", request.getRequestURI());

		if (!userSession.isAuthenticated()) {
			// Force redirection to /login if user is not authenticated
			if (request.getRequestURI().startsWith("/login") || request.getRequestURI().startsWith("/error")) {
				return true;
			} else {
				if (!"/".equals(request.getRequestURI())) {
					response.sendRedirect(response.encodeURL("/login") + "?targetUrl=" + request.getRequestURI());
				} else {
					response.sendRedirect("/login");
				}

				return false;
			}
		}
		// For authenticated users, redirect from / to /accounts
		else if ("/".equals(request.getRequestURI())) {
			response.sendRedirect("/accounts");
			return false;
		} else {
			return true;
		}

	}
}
