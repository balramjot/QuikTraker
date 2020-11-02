package org.balramjot.quiktraker.annotations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Email validation class that validates the email using regex
 * This class implements ConstraintValidator interface
 * It takes two arguments : an interface and value which needs to be validated
 * @author BjSaini
 * @version 1.0
 * 
 */
public class EmailValidator 
implements ConstraintValidator<ValidEmail, String> {
  
  private Pattern pattern;
  private Matcher matcher;
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*"
  		+ "@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$"; 
  
  @Override
  public void initialize(ValidEmail constraintAnnotation) {       
  }
  
  /**
   * This method checks if passed argument is not null and then passes the argument to another method
   * @param email
   * @return true if not null and false if null
   */
  public boolean isValid(String email, ConstraintValidatorContext context){   
      return (email != null) && (validateEmail(email));
  }
  
  /**
   * This method takes single argument and matches the pattern of the input with regex
   * @param email that is need to be validated
   * @return boolean, true if email is valid and false if email is not valid
   */
  private boolean validateEmail(String email) {
      pattern = Pattern.compile(EMAIL_PATTERN);
      matcher = pattern.matcher(email);
      return matcher.matches();
  }
}