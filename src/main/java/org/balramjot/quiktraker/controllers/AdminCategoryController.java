package org.balramjot.quiktraker.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.balramjot.quiktraker.models.ProductCategory;
import org.balramjot.quiktraker.services.AdminCategoryService;
import org.balramjot.quiktraker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller that controls all the product category related working
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class AdminCategoryController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	AdminCategoryService adminCategoryService;
	
	/**
	 * Initial method
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * @GET method
	 * @working - go to product category page
	 * redirect to the product category page
	 * with categories list and initial product category attributes
	 * @param formData to add form attributes
	 * @return name of the html page
	 */
	@GetMapping("/admin/category")
	public String productCategoryPage(Model formData) {
		List<ProductCategory> allCategory = adminCategoryService.showAll();
		ProductCategory productCategory = new ProductCategory();
		
		formData.addAttribute("formName", "save");
		formData.addAttribute("allCategory", allCategory);
		formData.addAttribute("productCategory", productCategory);
		
		return "admin/admin_product_category";
	}
	
	/**
	 * @POST method
	 * @working - to save product category to database
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * then, authenticate logged in user
	 * if authentication successful then, save the category
	 * else, show error message and stop code execution
	 * @param productCategory holds product category related entities
	 * @param result to bind values
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@PostMapping("/admin/createProductCategory")
	public String saveProductCategory(@Valid @ModelAttribute("productCategory") ProductCategory productCategory, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
		if(result.hasErrors()) {
			ArrayList<String> errorMessages = new ArrayList<String>();
			result.getAllErrors().stream().forEach(
					f -> { f.getDefaultMessage(); 
							errorMessages.add(f.getDefaultMessage());
							redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
					});
			return "redirect:/admin/category";
		}
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			adminCategoryService.save(productCategory);
			redirectAttributes.addFlashAttribute("message", "Product category has been added successfully");
			redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/category";
	}
	
	/**
	 * @GET method
	 * @working activate or deactivate product category
	 * method authenticate logged in user
	 * if authentication successful then, check for category i.e. not null
	 * if category status is true then makes it false, otherwise vice-versa
	 * if category is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * at last, save the update record
	 * @param id product category id
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@GetMapping("/admin/productCategoryStatus/{id}")
	public String activateDeactivateCategory(@PathVariable("id") long id, Model formData, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			ProductCategory category = adminCategoryService.findProductCategoryById(id);
			if(category != null) {
				
				if(category.getCategoryStatus() == true) {
					category.setCategoryStatus(false);
				} else {
					category.setCategoryStatus(true);
				}
				adminCategoryService.save(category);
				
				redirectAttributes.addFlashAttribute("message", "Product category has been updated successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", "Category not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/category";	
	}
	
	/**
	 * @GET method
	 * @working delete product category from database
	 * method authenticate logged in user
	 * if authentication successful then, check for category i.e. not null
	 * delete product category
	 * if category is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * @param id product category id
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@GetMapping("/admin/productCategoryRemove/{id}")
	public String removeCategory(@PathVariable("id") long id, Model formData, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			ProductCategory category = adminCategoryService.findProductCategoryById(id);
			if(category != null) {
				adminCategoryService.deleteProductById(id);
				
				redirectAttributes.addFlashAttribute("message", "Product category has been deleted successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", "Category not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/category";	
	}
	
	/**
	 * @GET method
	 * @working show edit product category page with fields
	 * method authenticate logged in user
	 * if authentication successful then, check for category i.e. not null
	 * retrieve list of product category and add it to form attributes
	 * if category is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * @param id product category id
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/admin/productCategoryEdit/{id}")
	public String editCategoryPage(@PathVariable("id") long id, Model formData, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			ProductCategory category = adminCategoryService.findProductCategoryById(id);
			if(category != null) {
				
				List<ProductCategory> allCategory = adminCategoryService.showAll();
				
				formData.addAttribute("formName", "update");
				formData.addAttribute("allCategory", allCategory);
				formData.addAttribute("productCategory", category);
				
			} else {
				redirectAttributes.addFlashAttribute("message", "Category not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "admin/admin_product_category";	
	}
	
	/**
	 * @POST method
	 * @working update product category
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * otherwise authenticate logged in user
	 * if authentication successful then, check for category i.e. not null
	 * update changes to the database
	 * if category is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * @param id product category id
	 * @param result to bind values
	 * @param productCategory holds product category related entities
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@PostMapping("/admin/updateProductCategory/{id}")
	public String updateProductCategory(@PathVariable("id") long id, @Valid @ModelAttribute("productCategory") ProductCategory productCategory, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
		if(result.hasErrors()) {
			ArrayList<String> errorMessages = new ArrayList<String>();
			result.getAllErrors().stream().forEach(
					f -> { f.getDefaultMessage(); 
							errorMessages.add(f.getDefaultMessage());
							redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
					});
			return "redirect:/admin/ProductCategoryEdit/{id}";
		}
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			ProductCategory category = adminCategoryService.findProductCategoryById(id);
			if(category != null) {
				
				category.setCategoryName(productCategory.getCategoryName());
				adminCategoryService.save(category);
				redirectAttributes.addFlashAttribute("message", "Product category has been updated successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
	
				
			} else {
				redirectAttributes.addFlashAttribute("message", "Category not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}

		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/category";
	}
	
	
}
