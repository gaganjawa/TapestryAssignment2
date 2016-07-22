package com.psl.tapestry.pages;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.psl.tapestry.service.UserService;

/**
 * This class represents login functionality for the user.
 * @author gagan_jawa
 *
 */
@Import(stylesheet="login.css")
public class Login {
	
	@Inject
	private Messages messages;
	
	@Inject
	private Logger logger;

	@Inject
	private AlertManager alertManager;
	
	@InjectPage
	private Index index;
	
	@InjectPage
	private UserView userView;
	
	@InjectPage
	private Register register;

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
	
	@Persist
	private Map<String, String> socialProfile;
	
	@InjectService("facebookService")
	private OAuth2ServiceProvider<Facebook> facebookService;
	
	@Persist
	@Property
	private String errorMessage;
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext.getBean("userService");
	
	public Map<String, String> getSocialProfile() {
		return socialProfile;
	}

	void onValidateFromLogin() {
		
		try {
			com.psl.tapestry.entities.User user = userService.findUserByEmail(email);
			
			if (user != null) {
				if (!user.getPassword().equals(password)) {
					login.recordError(passwordField, "Invalid credentials");
				}
			}
			
			if (email.equals("users@tapestry.apache.org") && !password.equals("Tapestry5")) {
				login.recordError(passwordField, "Try with password: Tapestry5");
			}
		} catch (NullPointerException e) {
			login.recordError("User doesn't exist");
		}

	}

	Object onSuccessFromLogin() {
		
		alertManager.success("Welcome aboard!");

		return userView;
	}

	void onFailureFromLogin() {
		logger.warn("Login error!");
		alertManager.error("I'm sorry but I can't log you in!");
	}
	
	/**
	 * FB profile is fetched on successful connection.
	 * 
	 * @param accessToken
	 */
	@OnEvent(value = EventConstants.SUCCESS, component = "facebook")
	void facebookConnected(final String accessToken) {
		final User profile = facebookService.getApi(accessToken).userOperations().getUserProfile();
		System.out.println(profile);
		
		errorMessage = null;
		socialProfile = new HashMap<String, String>();
		socialProfile.put("id", String.valueOf(profile.getId()));
		socialProfile.put("firstname", profile.getFirstName());
		socialProfile.put("lastname: " , profile.getLastName());
		socialProfile.put("email", profile.getEmail());
		socialProfile.put("gender", profile.getGender());
		socialProfile.put("locale", String.valueOf(profile.getLocale()));
		socialProfile.put("link", profile.getLink());
		
		System.out.println(socialProfile);
		
		register.facebookRegister(socialProfile);
		
	}
	/**
	 * This methods is called when 
	 * connection to facebook fails.
	 * 
	 */
	@OnEvent(value = EventConstants.FAILURE, component = "facebook")
	void facebookFailure() {
		socialProfile = null;
		System.out.println(EventConstants.FAILURE);
		errorMessage = messages.format("connection-denied", "Facebook");
	}
	
	Object onActionFromRegister() {
		return Register.class;
	}

}
