package com.psl.tapestry.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.service.UserService;

/**
 * 
 * @author gagan_jawa
 *
 */
public class About {

	@PageActivationContext
	private String learn;
	
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext.getBean("userService");

	private User user;

	@Property
	private List<User> users;

	@Inject
	private Messages messages;

	@Inject
	private BeanModelSource beanModelSource;
	
	private int rowNoWithCurrentPage;

	@Property
	private int noOfRowsPerPage = 4;

	public String getLearn() {
		return learn;
	}

	public void setLearn(String learn) {
		this.learn = learn;
	}

	@InjectComponent(value = "myGrid")
	private Grid grid;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		 rowNoWithCurrentPage++;  
	}

	// public List<UserUI> getUsers() {
	// // UserUI userUI = new UserUI("Rajiv", "Khan", "rajiv_khan",
	// "mainhoonkhan", "khan@khanna.com", "129743", true, false);
	// // users.add(userUI);
	// System.out.println(users);
	// return users;
	// }

	/**
	 * Initialise list. Usually, it should be injected.
	 * 
	 * @throws Exception
	 */
	public void pageLoaded() throws Exception {
		try {
			users = new ArrayList<User>();
			users = userService.getAllUsers();
			System.out.println(users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	public BeanModel<User> getBeanModel() {
		BeanModel<User> beanModel = beanModelSource.createDisplayModel(User.class, messages);
        beanModel.add("rowNo", null).label("Row No");  

		return beanModel;
	}
	
	public int getRowNo() {  
        int page = grid.getCurrentPage();  
        int rowNo = (page - 1) * noOfRowsPerPage + rowNoWithCurrentPage;  
        
        return rowNo;  
    }

}
