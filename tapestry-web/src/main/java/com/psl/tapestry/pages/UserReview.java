package com.psl.tapestry.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.service.UserService;

/**
 * View User details.
 * @author gagan_jawa
 *
 */
public class UserReview {

	// The activation context

	@Property
	private int userId;

	// Screen fields

	@Property
	private User user;

	// Generally useful bits and pieces
	@Inject
	private Messages messages;

	// The code

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext
			.getBean("userService");

	void onActivate(int userId) {
		this.userId = userId;
	}

	int onPassivate() {
		return userId;
	}

	void setupRender() {
		user = userService.findUserById(userId);
	}

}
