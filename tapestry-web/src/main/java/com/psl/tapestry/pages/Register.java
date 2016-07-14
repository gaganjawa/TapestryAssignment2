package com.psl.tapestry.pages;

import org.apache.tapestry5.annotations.Property;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.service.UserService;
import com.psl.tapestry.ui.UserUI;

/**
 * Registeration Page for users
 * 
 * @author gagan_jawa
 *
 */
public class Register {

	@Property
	private UserUI user;

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext
			.getBean("userService");;

	Object onSuccess() {
		System.out.println(user.toString());
		userService.createUser(new User(user.firstName, user.lastName,
				user.userName, user.password, user.email, user.mobile, true,
				false));
		return Login.class;
	}

}
