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
 * @author BjSaini
 * @version 1.0
 */

@Target({ElementType.TYPE}) 
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StockQuantityValidator.class)
@Documented
public @interface ValidStockQuantity {
	String message() default "Alloted quantity should be greater than quantity in stock";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
