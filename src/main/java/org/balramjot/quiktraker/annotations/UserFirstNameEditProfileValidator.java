package org.balramjot.quiktraker.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.models.UserInfo;

/**
 * UserFirstNameEditProfileValidator class that validates if first name is not null
 * This class implements ConstraintValidator interface
 * It takes two arguments : an interface and value which needs to be validated
 * @author BjSaini
 * @version 1.0
 */
public class UserFirstNameEditProfileValidator implements ConstraintValidator<ValidFirstNameEditProfile, UserInfo> {
	
	  @Override
	  public void initialize(ValidFirstNameEditProfile constraintAnnotation) {     
	  }
	  
	  /**
	   * This method checks if passed argument is not null
	   * @param object
	   * @return true if not null and false if null
	   */
	  @Override
	  public boolean isValid(UserInfo userInfo, ConstraintValidatorContext context){  
		  User usr = userInfo.getUser();
		  return ((usr != null) &&  (!usr.getFirstName().isEmpty()));
	     
	  } 
	  
}
