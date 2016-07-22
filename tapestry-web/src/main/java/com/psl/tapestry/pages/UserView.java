package com.psl.tapestry.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.Request;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.exception.NoUserPresentException;
import com.psl.tapestry.service.UserService;

/**
 * User Page after user signs in.
 * 
 * @author gagan_jawa
 *
 */
public class UserView {
	
	@Property
	private int userId;

	@Inject
	private Request request;

	private User user;

	@SessionState
	@Property
	private List<User> users;

	@Inject
	private Messages messages;

	@Inject
	private BeanModelSource beanModelSource;

	private int rowNoWithCurrentPage;

	@Property
	private int noOfRowsPerPage = 4;

	@InjectComponent(value = "myGrid")
	private Grid grid;
	
	 // The code

    void onActivate(int userId) {
        this.userId = userId;
    }

    Integer onPassivate() {
        return userId;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		rowNoWithCurrentPage++;
	}

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext.getBean("userService");

	/**
	 * Initialise list. Usually, it should be injected.
	 * 
	 * @throws Exception
	 */
	public void pageLoaded() throws Exception {
		try {
			users = new ArrayList<User>();
			users = userService.getAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public BeanModel<User> getBeanModel() {
		BeanModel<User> beanModel = beanModelSource.createDisplayModel(
				User.class, messages);
		beanModel.add("actions", null).label("Actions");
		beanModel.add("rowNo", null).label("Row No");
		beanModel.get("firstName").label("First Name");
		beanModel.exclude("password");
		beanModel.exclude("username");
		beanModel.exclude("enabled");
		beanModel.exclude("locked");

		return beanModel;
	}

	public int getRowNo() {
		int page = grid.getCurrentPage();
		int rowNo = (page - 1) * noOfRowsPerPage + rowNoWithCurrentPage;

		return rowNo;
	}

	public int getActions() {
		int page = grid.getCurrentPage();
		int rowNo = (page - 1) * noOfRowsPerPage + rowNoWithCurrentPage;

		return rowNo;
	}
	
	void onDelete(int id) throws NoUserPresentException {
		user = userService.findUserById(id);
		users.remove(user);
		userService.deleteUser(id);
		return;
	}
	

}
