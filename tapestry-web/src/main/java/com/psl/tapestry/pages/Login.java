package com.psl.tapestry.pages;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.service.UserService;

@Import(stylesheet="login.css")
public class Login {
	
	@Inject
	private Logger logger;

	@Inject
	private AlertManager alertManager;
	
	@InjectPage
	private Index index;
	
	@InjectPage
	private UserView userView;

	@InjectComponent
	private Form login;

	@InjectComponent("email")
	private TextField emailField;

	@InjectComponent("password")
	private PasswordField passwordField;

	@Property
	private String email;

	@Property
	private String password;
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext.getBean("userService");
	

	void onValidateFromLogin() {
		
		try {
			User user = userService.findUserByEmail(email);
			
			if (user != null) {
				if (!user.getPassword().equals(password)) {
					login.recordError(passwordField, "Invalid credentials");
				}
			}
			
//			if (!email.equals("users@tapestry.apache.org") || user != null) {
//				login.recordError(emailField,
//						"Try with user: users@tapestry.apache.org");
//			}

			if (email.equals("users@tapestry.apache.org") && !password.equals("Tapestry5")) {
				login.recordError(passwordField, "Try with password: Tapestry5");
			}
		} catch (NullPointerException e) {
			login.recordError("User doesn't exist");
		}
		
		

	}

	Object onSuccessFromLogin() {
		logger.info("Login successful :: " + userService.testMethod("Gagan"));
		
//		userView.setUsers(userService.getAllUsers());
		
		alertManager.success("Welcome aboard!");

		return userView;
	}

	void onFailureFromLogin() {
		logger.warn("Login error!");
		alertManager.error("I'm sorry but I can't log you in!");
	}
	
	
	Object onActionFromRegister() {
		return Register.class;
	}

}
