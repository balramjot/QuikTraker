package org.balramjot.quiktraker.services;

import java.util.List;

import org.balramjot.quiktraker.dao.AdminCategoryRepoIF;
import org.balramjot.quiktraker.models.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for product category controller
 * @author BjSaini
 * @version 1.0
 */
@Service
public class AdminCategoryService {
	
	/**
	 * connecting the product category repository
	 */
	@Autowired
	AdminCategoryRepoIF adminCategoryRepoIF;
	
	/**
	 * method to save product category
	 * @param u which is product category
	 */
	public void save(ProductCategory u) {
		adminCategoryRepoIF.save(u);
	}
	
	/**
	 * method to show list of all product categories sorted according to creation date time
	 * @return list of product category
	 */
	public List<ProductCategory> showAll() {
		return adminCategoryRepoIF.findAllByOrderByCreationDateTimeDesc();
	}
	
	/**
	 * method to check if product category exist in the database or not
	 * @param id - category id
	 * @return boolean - true if product category exists and false if not
	 */
	public boolean existById(long id) {
		return adminCategoryRepoIF.existsById(id);
	}
	
	/**
	 * method to get list of product category if exist in the database
	 * @param id - category id
	 * @return product category if exist otherwise null
	 */
	public ProductCategory findProductCategoryById(long id) {
		if(existById(id))
			return adminCategoryRepoIF.findById(id).get();
		else
			return null;
	}
	
	/**
	 * method to delete product category if exist in the database
	 * @param id - category id
	 */
	public void deleteProductById(long id) {
		if(existById(id))
			adminCategoryRepoIF.deleteById(id);
	}
	
	/**
	 * method to get list of all product categories where product category status is true and sorted according to creation date time
	 * @return list of product category
	 */
	public List<ProductCategory> showAllByStatus() {
		return adminCategoryRepoIF.findByCategoryStatusTrueOrderByCreationDateTimeDesc();
	}
	
}
