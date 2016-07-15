package com.psl.tapestry.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.psl.tapestry.components.SimpleModal;
import com.psl.tapestry.entities.User;
import com.psl.tapestry.exception.NoUserPresentException;
import com.psl.tapestry.service.UserService;

/**
 * User View after user sign in.
 * 
 * @author gagan_jawa
 *
 */
public class UserView {

	public enum Function {
		REVIEW, UPDATE;
	}
	
	@Property
	private int userId;

	private Function function;

	@InjectComponent
	private Zone paneZone;

	@InjectComponent
	private Zone modalZone;

	@InjectComponent
	private SimpleModal personUpdateModal;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	@Inject
	private JavaScriptSupport javaScriptSupport;

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

	ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"SpringConfiguration.xml");

	UserService userService = (UserService) applicationContext
			.getBean("userService");

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

	void onUpdate(int id) {
		this.userId = id;
		function = Function.UPDATE;
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender(modalZone);
		}
	}
	
	void onUpdatedFromPersonUpdate(User user) {
        this.userId = user.getId();
        function = Function.REVIEW;

        personUpdateModal.hide();

        populateBody();

        if (request.isXHR()) {
            ajaxResponseRenderer.addRender(paneZone);
        }
    }
	
	void onCanceledFromPersonUpdate(int userId) {
        this.userId = userId;
        function = Function.REVIEW;

        personUpdateModal.hide();

        populateBody();

        if (request.isXHR()) {
            ajaxResponseRenderer.addRender(paneZone);
        }
    }

        
	public void populateBody() {

        user = userService.findUserById(userId);

        if (user == null) {
            throw new IllegalStateException("Database data has not been set up!");
        }
    }


	public void onActionFromDelete(int id) throws NoUserPresentException {
		users.remove(userService.findUserById(id));
		userService.deleteUser(id);
	}
	
	public boolean isFunction(Function function) {
        return function == this.function;
    }

}
