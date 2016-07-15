package com.psl.tapestry.pages;

import org.apache.tapestry5.annotations.PageActivationContext;

/**
 * About Page for TapestryAssignment2
 * 
 * @author gagan_jawa
 *
 */
public class About {

	@PageActivationContext
	private String learn;

	public String getLearn() {
		return learn;
	}

	public void setLearn(String learn) {
		this.learn = learn;
	}	

}
