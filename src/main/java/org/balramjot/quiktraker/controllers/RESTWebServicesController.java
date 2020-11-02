package org.balramjot.quiktraker.controllers;

import java.util.List;

import org.balramjot.quiktraker.exceptions.CustomException;
import org.balramjot.quiktraker.models.ProductCategory;
import org.balramjot.quiktraker.services.AdminCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController
 * @author BjSaini
 * @version 1.0
 *
 */
@RestController
public class RESTWebServicesController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	AdminCategoryService adminCategoryService;
	
	/**
	 * @GET
	 * @working to get list of all the product categories using rest web services
	 * @return list of the products category
	 */
	@GetMapping("/rest/allCategory")
	public List<ProductCategory> getAllUsersListRest() {
		List<ProductCategory> restCategory = adminCategoryService.showAll();
		return restCategory;
	}
	
	/**
	 * @GET
	 * @working to get product category according to the category id
	 * @param id which is category id
	 * @return single product category
	 */
	@GetMapping({"/rest/category/{id}"})
	public ProductCategory getAllCategoryListRest(@PathVariable long id) {
		if(adminCategoryService.findProductCategoryById(id) == null) throw new CustomException("Product category not exist");
		return adminCategoryService.findProductCategoryById(id);
	}
}
