package com.psl.tapestry.pages;

import java.util.Map;

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
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext.getBean("userService");

	/**
	 * onSuccess of register form. This methods registers user 
	 * and saves user details in database.
	 * 
	 * @return Login page object
	 */
	Object onSuccess() {
		System.out.println(user.toString());
		userService.createUser(new User(user.firstName, user.lastName,
				user.userName, user.password, user.email, user.mobile, true,
				false));
		return Login.class;
	}
	
	/**
	 * Registers user using FB profile.
	 * @param socialProfile
	 * @return UserView page
	 */
	public Object facebookRegister(Map<String, String> socialProfile) {
		userService.createUser(new User((socialProfile.get("firstname")),(socialProfile.get("lastname")),
				(socialProfile.get("email")),"",(socialProfile.get("email")),"", true,
				false));
		return UserView.class;
	}

}
