package com.psl.tapestry.exception;

public class NoUserPresentException extends Exception {

	/**
	 * Exception thrown when no user data is present in db.
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoUserPresentException(String mesage) {
		super(mesage);
	}
}
