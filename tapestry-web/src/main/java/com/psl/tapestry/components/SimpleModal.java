package com.psl.tapestry.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class SimpleModal implements ClientElement {
	
	@Parameter(name = "componentClientId", value="prop:componentResources.id", defaultPrefix = BindingConstants.LITERAL)
	private String componentClientId;
	
	@Inject
	private JavaScriptSupport javaScriptSupport;
	
	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;
	
	@Inject
	private Request request;

	@Override
	public String getClientId() {
		return componentClientId;
	}
	
	void afterRender() {
		javaScriptSupport.require("simple-modal.js").invoke("activate").with(componentClientId, new JSONObject());
	}
	
	public void hide() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addCallback(makeScriptToHideModal());
		}
	}

	private JavaScriptCallback makeScriptToHideModal() {
		return new JavaScriptCallback() {
			
			@Override
			public void run(JavaScriptSupport javaScriptSupport) {
				javaScriptSupport.require("simple-modal").invoke("hide").with(componentClientId);
			}
		};
	}

}
