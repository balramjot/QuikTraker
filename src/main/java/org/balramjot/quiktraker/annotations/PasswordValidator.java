package org.balramjot.quiktraker.annotations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Password validation class that validates the password using regex
 * This class implements ConstraintValidator interface
 * It takes two arguments : an interface and value which needs to be validated
 * @author BjSaini
 * @version 1.0
 * 
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String>{
	  private Pattern pattern;
	  private Matcher matcher;
	  private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{1,}$";
	  @Override
	  public void initialize(ValidPassword constraintAnnotation) {       
	  }
	  
	  /**
	   * This method checks if passed argument is not null
	   * @param password
	   * @return true if not null and false if null
	   */
	  @Override
	  public boolean isValid(String password, ConstraintValidatorContext context){   
	      return (password != null) && (validatePassword(password));
	  }
	  
	  /**
	   * This method takes single argument and matches the pattern of the input with regex
	   * @param password that is need to be validated
	   * @return boolean, true if password is valid and false if password is not valid
	   */
	  private boolean validatePassword(String password) {
	      pattern = Pattern.compile(PASSWORD_PATTERN);
	      matcher = pattern.matcher(password);
	      return matcher.matches();
	  }
}
