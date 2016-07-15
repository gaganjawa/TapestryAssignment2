package com.psl.tapestry.util;

import java.util.ArrayList;
import java.util.List;


public class DummyData {

	public static String firstName = "Dummy";
	public static String lastName = "Dummy";
	public static String username = "dummy_123";
	public static String password = "45678913";
	public static String email = "abcd@psl.com";
	public static String mobile = "7809456130";
	public static boolean enabled = true;
	public static boolean locked = false;
	
	public static List<Object> getDummyData() {
		return new ArrayList<Object>();
	}
}
