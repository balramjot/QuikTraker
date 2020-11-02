package org.balramjot.quiktraker.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class to handle custom exception
 * Class AppExceptionHandler inherits properties from ResponseEntityExceptionHandler class
 * @ControllerAdvice - to use custom exception message globally
 * @author BjSaini
 * @version 1.0
 *
 */
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * method handles custom exception and send to the controller
	 * @param exception
	 * @return exception message
	 */
	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<Object> exception(CustomException exception) {
		 return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
}
