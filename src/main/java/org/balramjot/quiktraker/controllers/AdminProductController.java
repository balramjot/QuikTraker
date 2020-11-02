package org.balramjot.quiktraker.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.balramjot.quiktraker.models.Product;
import org.balramjot.quiktraker.models.ProductCategory;
import org.balramjot.quiktraker.models.Transaction;
import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.services.AdminCategoryService;
import org.balramjot.quiktraker.services.AdminProductService;
import org.balramjot.quiktraker.services.TransactionService;
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
 * Controller that controls all the product related working
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class AdminProductController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	AdminCategoryService adminCategoryService;
	@Autowired
	AdminProductService adminProductService;
	@Autowired
	TransactionService transactionService;
	
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
	 * @working - go to product page for administrator
	 * redirect to the product page for administrator
	 * with products list and categories list 
	 * along with initial product attributes
	 * @param formData to add form attributes
	 * @return name of the html page
	 */
	@GetMapping("/admin/products")
	public String productsPage(Model formData) {
		List<Product> allProduct = adminProductService.showAll();
		List<ProductCategory>  allCategory = adminCategoryService.showAllByStatus();
		Product newProduct = new Product();
		
		formData.addAttribute("formName", "save");
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("newProduct", newProduct);
		formData.addAttribute("allCategory", allCategory);
		
		return "admin/admin_product";
	}
	
	/**
	 * @POST
	 * @working creating new product
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * then, authenticate logged in user
	 * if authentication successful then, save the product in products table
	 * and create a new transaction record in transaction table under administrator id with quantity in stock value as aquantity
	 * else, show error message and stop code execution
	 * @param newProduct holds product related entities
	 * @param result to bind values
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return name of the html page
	 */
	@PostMapping("/admin/createProduct")
	public String saveProductCategory(@Valid @ModelAttribute("newProduct") Product newProduct, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
		if(result.hasErrors()) {
			ArrayList<String> errorMessages = new ArrayList<String>();
			result.getAllErrors().stream().forEach(
					f -> { f.getDefaultMessage(); 
							errorMessages.add(f.getDefaultMessage());
							redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
					});
			return "redirect:/admin/products";
		}
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			User user = userService.checkEmailAvailability(authentication.getName());
			adminProductService.save(newProduct);
			
			Transaction transaction = new Transaction();
			transaction.setQuantity(newProduct.getInStock());
			transaction.setProduct(newProduct);
			transaction.setUser(user);
			transaction.setTransactionType("deposit");
			transaction.setQuantityRemaining(newProduct.getInStock());
			transactionService.save(transaction);
			
			redirectAttributes.addFlashAttribute("message", "Product has been added successfully");
			redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/products";
	}
	
	/**
	 * @GET method
	 * @working activate or deactivate product
	 * method authenticate logged in user
	 * if authentication successful then, check for product i.e. not null
	 * if product status is true then makes it false, otherwise vice-versa
	 * if product is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * at last, save the update record
	 * @param id product id
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@GetMapping("/admin/productStatus/{id}")
	public String activateDeactivateProduct(@PathVariable("id") long id, Model formData, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			Product product = adminProductService.findProductById(id);
			if(product != null) {
				
				if(product.getProductStatus() == true) {
					product.setProductStatus(false);
				} else {
					product.setProductStatus(true);
				}
				adminProductService.save(product);
				
				redirectAttributes.addFlashAttribute("message", "Product has been updated successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", "Product not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/products";	
	}
	
	/**
	 * @GET method
	 * @working delete product from database
	 * method authenticate logged in user
	 * if authentication successful then, check for product i.e. not null
	 * delete product
	 * if product is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * @param id product id
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@GetMapping("/admin/productRemove/{id}")
	public String removeProduct(@PathVariable("id") long id, Model formData, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			Product product = adminProductService.findProductById(id);
			if(product != null) {
				adminProductService.deleteProductById(id);
				
				redirectAttributes.addFlashAttribute("message", "Product has been deleted successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", "Product not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/products";	
	}
	
	/**
	 * @GET method
	 * @working show edit product page with fields
	 * method authenticate logged in user
	 * if authentication successful then, check for product i.e. not null
	 * retrieve list of product and category then, add it to form attributes
	 * if product is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * @param id product id
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/admin/productEdit/{id}")
	public String editProductPage(@PathVariable("id") long id, Model formData, RedirectAttributes redirectAttributes, Authentication authentication) {
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			Product product = adminProductService.findProductById(id);
			if(product != null) {
				List<Product> allProduct = adminProductService.showAll();
				List<ProductCategory>  allCategory = adminCategoryService.showAllByStatus();
				
				formData.addAttribute("formName", "update");
				formData.addAttribute("newProduct", product);
				formData.addAttribute("allProduct", allProduct);
				formData.addAttribute("allCategory", allCategory);
			} else {
				redirectAttributes.addFlashAttribute("message", "Category not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "admin/admin_product";	
	}
	
	/**
	 * @POST method
	 * @working update product
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * otherwise authenticate logged in user
	 * if authentication successful then, check for product i.e. not null
	 * update changes to the database
	 * if product is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * @param id product id
	 * @param newProduct holds product related entities
	 * @param result to bind values
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@PostMapping("/admin/updateProduct/{id}")
	public String updateProductCategory(@PathVariable("id") long id, @Valid @ModelAttribute("newProduct") Product newProduct, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
		if(result.hasErrors()) {
			ArrayList<String> errorMessages = new ArrayList<String>();
			result.getAllErrors().stream().forEach(
					f -> { f.getDefaultMessage(); 
							errorMessages.add(f.getDefaultMessage());
							redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
					});
			return "redirect:/admin/productEdit/{id}";
		}
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			Product product = adminProductService.findProductById(id);
			if(product != null) {
				
				product.setProductName(newProduct.getProductName());
				product.setProductDescription(newProduct.getProductDescription());
				product.setAllotedQuantity(newProduct.getAllotedQuantity());
				product.setProductImage(newProduct.getProductImage());
				product.setProductCategory(newProduct.getProductCategory());
				
				adminProductService.save(product);
				redirectAttributes.addFlashAttribute("message", "Product has been updated successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
	
				
			} else {
				redirectAttributes.addFlashAttribute("message", "Product not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}

		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/products";
	}
	
	/**
	 * @GET
	 * @working show list of low stock products i.e. products with in stock quantity less than 25% of alloted quantity
	 * method catches values of low stock products in list and add to form attributes
	 * @param formData to add form attributes
	 * @return html page
	 */
	@GetMapping("/admin/lowstockproducts")
	public String lowStockProductsPage(Model formData) {
		List<Product> allProduct = adminProductService.showAllLowQuantity();
		formData.addAttribute("allProduct", allProduct);
		
		return "admin/admin_lowstock_product";
	}
	
	/**
	 * @GET
	 * @working show list of out of stock products in stock
	 * method catches values of out of stock products in list and add to form attributes
	 * @param formData to add form attributes
	 * @return html page
	 */
	@GetMapping("/admin/outofstockproducts")
	public String outOfStockProductsPage(Model formData) {
		List<Product> allProduct = adminProductService.showAllOutOfStock();
		formData.addAttribute("allProduct", allProduct);
		
		return "admin/admin_outofstock_product";
	}
	
}
