package com.psl.tapestry.ui;

import org.apache.tapestry5.beaneditor.Validate;

public class UserUI {
	
	@Validate("required")
	public String firstName;

	public String lastName;

	public String userName;

	public String password;

	@Validate("required")
	public String email;

	@Validate("required")
	public String mobile;
	
}
