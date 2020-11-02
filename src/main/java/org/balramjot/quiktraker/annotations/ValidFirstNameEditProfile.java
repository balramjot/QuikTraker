package org.balramjot.quiktraker.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * Interface for validation
 * @author saini
 * @version 1.0
 */

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserFirstNameEditProfileValidator.class)
@Documented
public @interface ValidFirstNameEditProfile {
	String message() default "Invalid User First Name";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
