package fr.ing.secu.leakybank.pages.login;


import javax.validation.constraints.NotBlank;

public class LoginForm {
	
	@NotBlank(message="Login is required.")
	private String login;
	
	@NotBlank(message="Password is required.")
	private String password;
	
	/**
	 * Optional, target url after login success
	 */
	private String targetUrl;
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}


}
