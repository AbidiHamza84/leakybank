package fr.ing.secu.leakybank;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main class of the application
 * 
 * @author chouippea
 *
 */
@EnableAutoConfiguration
@ComponentScan
@Configuration
public class Application implements WebMvcConfigurer, WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
	final
	ConfigurableServletWebServerFactory webServerFactory;

	public Application(ConfigurableServletWebServerFactory webServerFactory, AuthenticatedInterceptor authenticatedInterceptor) {
		this.webServerFactory = webServerFactory;
		this.authenticatedInterceptor = authenticatedInterceptor;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	private final AuthenticatedInterceptor authenticatedInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticatedInterceptor);
	}

	@Bean
	@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public UserSession userSession() {
		return new UserSession();
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		try {
			factory.setAddress(InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			throw new IllegalArgumentException("Cannot bind server to 127.0.0.1 address: '" + e.getMessage() + "'", e);
		}
	}
}