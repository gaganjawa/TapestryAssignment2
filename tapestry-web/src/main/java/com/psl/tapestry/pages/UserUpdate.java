package com.psl.tapestry.pages;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.service.UserService;

public class UserUpdate {

	@Inject
	private AlertManager alertManager;
	
	@Property
	private int userId;
	
	@SessionState
	@Property
	private List<User> users;
	
	@Property
	private String firstName;

	@Property
	private User user;

	@InjectComponent
	private Form form;
	
	private int i=0;

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext
			.getBean("userService");

	// The code
	
	@PageLoaded
	public void fetchUsers() {
		System.out.println("###########"+userId);
		users = userService.getAllUsers();
		System.out.println("!!!!!!!!!!!!!!!"+users);
	}

	void onActivate(int userId) {
		this.userId = userId;
	}

	int onPassivate() {
		return userId;
	}

	void onPrepareForRender() {

		// If fresh start, make sure there's a User object available.

		if (form.isValid()) {
			user = userService.findUserById(userId);
			// Handle null person in the template.
		}
		
	}

	void onPrepareForSubmit() {

		// Get objects for the form fields to overlay.
		user = userService.findUserById(userId);

		if (user == null) {
			form.recordError("User has been deleted by another process.");
			// Instantiate an empty person to avoid NPE in the Form.
			user = new User();
		}
	}
	
	void onValidateFromForm() {

        if (form.getHasErrors()) {
            return;
        }

        try {
        	userService.updateUser(userId, user);
        }
        catch (Exception e) {
            // Display the cause. In a real system we would try harder to get a user-friendly message.
            form.recordError(ExceptionUtils.getRootCauseMessage(e));
        }
    }

	Object onSuccess() {
		
		alertManager.success("User updated.");
		return UserView.class;
	}
	
	Object onBackPressed() {
		
		return UserView.class;
		
	}

}
