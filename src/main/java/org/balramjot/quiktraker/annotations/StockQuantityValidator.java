package org.balramjot.quiktraker.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.balramjot.quiktraker.models.Product;

/**
 * StockQuantityValidator class that checks quantity to be entered should not be greater than alloted quantity
 * This class implements ConstraintValidator interface
 * It takes two arguments : an interface and value which needs to be validated
 * @author BjSaini
 * @version 1.0
 */
public class StockQuantityValidator implements ConstraintValidator<ValidStockQuantity,Product>{
	
	@Override
	  public void initialize(ValidStockQuantity constraintAnnotation) {       
	  }
	
	/**
	   * This method checks if passed argument is not null
	   * @param object
	   * @return true if not null and false if null
	   */
	public boolean isValid(Product value, ConstraintValidatorContext context) {
		try {
			return (value != null) && (validateStockQuantity(value));
		} catch (Exception e) {
			return false;
		}
		
	}
	
	/**
	   * This method checks quantity to be entered should not be greater than alloted quantity
	   * @param object
	   * @return true if quantity in stock is less than alloted quantity for that product
	   */
	private boolean validateStockQuantity(Product value) {
		return value.getInStock() <= value.getAllotedQuantity();
	}

}
