package com.psl.tapestry.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.exception.NoUserPresentException;
import com.psl.tapestry.service.UserService;
import com.psl.tapestry.util.DummyData;

@Controller
public class SpringDataApp {
	
	@Autowired
	static UserService dao;
	
	private static final Logger logger = Logger.getLogger(SpringDataApp.class.getName());
	
	public static void main(String[] args) throws NoUserPresentException {
		
		try {
//			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
//					"SpringConfiguration.xml");
//
//			UserService dao = (UserService) applicationContext.getBean("userService");
			
			User user = new User(DummyData.firstName, DummyData.lastName,
					DummyData.username, DummyData.password, DummyData.email,
					DummyData.mobile, DummyData.enabled, DummyData.locked);
			logger.info(user.toString());
			dao.createUser(user);
			List<User> users = dao.getAllUsers();
//			System.out.println(dao.findUserById(4));
			System.out.println("Users " + users);

//			((ConfigurableApplicationContext) applicationContext).close();

		} catch (BeansException e) {
			e.printStackTrace();
		}
	}

}
