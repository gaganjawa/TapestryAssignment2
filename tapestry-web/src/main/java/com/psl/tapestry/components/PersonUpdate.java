package com.psl.tapestry.components;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Events;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.service.UserService;

@Events({ PersonUpdate.CANCELED, PersonUpdate.UPDATED })
public class PersonUpdate {

	public static final String CANCELED = "canceled";
	public static final String UPDATED = "updated";

	@Parameter(required = true)
	@Property
	private int userId;

	@Property
	private User user;

	@InjectComponent
	private Form form;

	@InjectComponent
	private Zone formZone;

	@Inject
	private Request request;

	@Inject
	private ComponentResources componentResources;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext.getBean("userService");

	// The code

	boolean onCancel(int userId) {
		this.userId = userId;

		componentResources.triggerEvent(CANCELED, new Object[] { userId }, null);
		// We don't want the event to bubble up, so we return true to say we've
		// handled it.
		return true;
	}
	
	void onPrepareForRender() {

        // If fresh start, make sure there's a Person object available.

        if (form.isValid()) {
            user = userService.findUserById(userId);
            // Handle null person in the template.
        }
    }
	
	void onPrepareForSubmit(int userId) {
        this.userId = userId;

        // Get objects for the form fields to overlay.
        user = userService.findUserById(userId);

        if (user == null) {
            form.recordError("Person has been deleted by another process.");
            // Instantiate an empty person to avoid NPE in the Form.
            user = new User();
        }
    }
	
	 boolean onValidateFromForm() {

	        if (form.getHasErrors()) {
	            return true;
	        }

	        try {
	            user = userService.updateUser(userId, user);
	        }
	        catch (Exception e) {
	            // Display the cause. In a real system we would try harder to get a
	            // user-friendly message.
	            form.recordError(e.getMessage());
	        }

	        return true;
	    }
	 
	 boolean onSuccess() {
	        // We want to tell our containing page explicitly what person we've updated, so we trigger a new event with a
	        // parameter. It will bubble up because we don't have a handler method for it.
	        componentResources.triggerEvent(UPDATED, new Object[] { user }, null);

	        // We don't want the original event to bubble up, so we return true to
	        // say we've handled it.
	        return true;
	    }

	    boolean onFailure() {

	        if (request.isXHR()) {
	            ajaxResponseRenderer.addRender(formZone);
	        }

	        // We don't want the event to bubble up, so we return true to say we've
	        // handled it.
	        return true;
	    }

}
