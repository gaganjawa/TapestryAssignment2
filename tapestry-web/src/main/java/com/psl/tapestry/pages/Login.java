package com.psl.tapestry.pages;

import java.util.List;

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

import com.psl.tapestry.service.UserService;
import com.psl.tapestry.ui.UserUI;

@Import(stylesheet="login.css")
public class Login {
	
	@Inject
	private Logger logger;

	@Inject
	private AlertManager alertManager;
	
	@InjectPage
	private Index index;
	
	@InjectPage
	private About about;

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
	
	private List<UserUI> users;


	void onValidateFromLogin() {
		if (!email.equals("users@tapestry.apache.org") /*|| userService.findUserByEmail(email) != null*/) {
			login.recordError(emailField,
					"Try with user: users@tapestry.apache.org");
		}

		if (!password.equals("Tapestry5"))
			login.recordError(passwordField, "Try with password: Tapestry5");

	}

	Object onSuccessFromLogin() {
		logger.info("Login successful :: " + userService.testMethod("Gagan"));
		try {
			about.setLearn(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		alertManager.success("Welcome aboard!");

		return about;
	}

	void onFailureFromLogin() {
		logger.warn("Login error!");
		alertManager.error("I'm sorry but I can't log you in!");
	}
	
	Object onRegister() {
        return Index.class;
    }

	public List<UserUI> getUsers() {
		return users;
	}

	public void setUsers(List<UserUI> users) {
		this.users = users;
	}
	
	Object onActionFromRegister() {
		return Register.class;
	}

}
