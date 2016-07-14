package com.psl.tapestry.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.psl.tapestry.pages.Index;

public class SimpleLayout {

	@Parameter
    private String title = "";

    @Inject
    private Logger logger;

    Object onLogout() {
        
        return Index.class;
    }
}
