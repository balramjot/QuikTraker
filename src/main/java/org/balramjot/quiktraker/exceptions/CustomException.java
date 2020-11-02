package org.balramjot.quiktraker.exceptions;

/**
 * Class to hold custom exception for the rest web services
 * Class CustomException inherits properties from RuntimeException class
 * @author BjSaini
 * @version 1.0
 */
public class CustomException extends RuntimeException{

	/**
	 * constant variable to hold serial version id
	 */
	private static final long serialVersionUID = -8098526506822521010L;
	
	/**
	 * Constructor to hold exception message
	 * @param message
	 */
	public CustomException(String message) {
		super(message);
	}
}
