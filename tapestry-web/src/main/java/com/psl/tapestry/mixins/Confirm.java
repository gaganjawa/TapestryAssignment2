package com.psl.tapestry.mixins;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

@Import (library="confirm.js")
public class Confirm {

	@Parameter(value = "Are you sure?", defaultPrefix = BindingConstants.LITERAL)
	private String message;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	@InjectContainer
	private ClientElement element;

	@AfterRender
	public void afterRender() {
		javaScriptSupport.addInitializerCall("confirmation", new JSONObject("id", this.element.getClientId(), "message",  this.message));//addScript("new Confirm('%s', '%s');", element.getClientId(), message);
	}

}
