package org.balramjot.quiktraker.controllers;

import java.util.ArrayList;
import java.util.List;

import org.balramjot.quiktraker.models.Product;
import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.services.AdminProductService;
import org.balramjot.quiktraker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Controller that controls all the inventory related working for user
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class UserInventoryController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	AdminProductService adminProductService;
	
	/**
	 * Initial method
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * @GET
	 * @working show inventory page for user
	 * method get all the products list, get logged in user details, initialize new product for search product
	 * and add them to respective form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/inventory")
	public String inventoryPage(Model formData, Authentication authentication) {
		List<Product> allProduct = adminProductService.showAllByStatus();
		User user = userService.checkEmailAvailability(authentication.getName());
		Product searchProduct = new Product();
		
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("loggedInUserId", user.getUserId());
		formData.addAttribute("searchProduct", searchProduct);
		return "inventory";
	}
	
	/**
	 * @GET
	 * @working show out of stock products page
	 * method get all the out of stock products list along with the rest of products,
	 * sorted according to out of stock products first followed by rest of the products
	 * initialize new product for search product
	 * and add them to respective form attributes
	 * @param formData to add form attributes
	 * @return html page
	 */
	@GetMapping("/user/outofstockproducts")
	public String outOfStockProductsPage(Model formData) {
		List<Product> allProduct = adminProductService.showAllOutOfStock();
		Product searchProduct = new Product();
		
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("searchProduct", searchProduct);
		return "inventory";
	}
	
	/**
	 * @GET
	 * @working filter according to oldest product first
	 * method get oldest added product first, get logged in user details, initialize new product for search product
	 * and add them to respective form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/user/oldestProduct")
	public String oldProductsFirstPage(Model formData, Authentication authentication) {
		List<Product> allProduct = adminProductService.showAllOldestProduct();
		User user = userService.checkEmailAvailability(authentication.getName());
		Product searchProduct = new Product();
		
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("loggedInUserId", user.getUserId());
		formData.addAttribute("inventoryFilter", "Oldest Products");
		formData.addAttribute("searchProduct", searchProduct);
		return "inventory";
	}
	
	/**
	 * @GET
	 * @working filter according low in stock to high in stock products
	 * method get low in stock added product first followed by high in stock,
	 * get logged in user details, initialize new product for search product
	 * and add them to respective form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/user/lowToHigh")
	public String lowToHighProductsPage(Model formData, Authentication authentication) {
		List<Product> allProduct = adminProductService.showAllLowToHighProduct();
		User user = userService.checkEmailAvailability(authentication.getName());
		Product searchProduct = new Product();
		
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("loggedInUserId", user.getUserId());
		formData.addAttribute("inventoryFilter", "Low to High");
		formData.addAttribute("searchProduct", searchProduct);
		return "inventory";
	}
	
	/**
	 * @GET
	 * @working filter according high in stock to low in stock products
	 * method get high in stock added product first followed by low in stock,
	 * get logged in user details, initialize new product for search product
	 * and add them to respective form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/user/highToLow")
	public String highToLowProductsPage(Model formData, Authentication authentication) {
		List<Product> allProduct = adminProductService.showAllHighToLowProduct();
		User user = userService.checkEmailAvailability(authentication.getName());
		Product searchProduct = new Product();
		
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("loggedInUserId", user.getUserId());
		formData.addAttribute("inventoryFilter", "High to Low");
		formData.addAttribute("searchProduct", searchProduct);
		return "inventory";
	}
	
	/**
	 * @GET
	 * @working filter according lrecently added products
	 * method get latest added product first followed by oldest added,
	 * get logged in user details, initialize new product for search product
	 * and add them to respective form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/user/recentlyAdded")
	public String recentlyAddedProductPage(Model formData, Authentication authentication) {
		List<Product> allProduct = adminProductService.showAllByStatus();
		User user = userService.checkEmailAvailability(authentication.getName());
		Product searchProduct = new Product();
		
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("loggedInUserId", user.getUserId());
		formData.addAttribute("inventoryFilter", "Recently Added");
		formData.addAttribute("searchProduct", searchProduct);
		return "inventory";
	}
	
	/**
	 * @GET
	 * @working to search product according to product name, product category and product description
	 * method get list of products after search,
	 * get logged in user details, initialize new product for search product
	 * and add them to respective form attributes
	 * @param searchProduct product class entity
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/user/searchProduct")
	public String searchProductPage(@ModelAttribute("searchProduct") Product searchProduct, Model formData, Authentication authentication) {
		List<Product> allProduct = new ArrayList<>();
		
		if(searchProduct.getSearchParam() != null) {
			allProduct = adminProductService.searchProduct(searchProduct.getSearchParam());
		} else {
			allProduct = adminProductService.showAllByStatus();
		}
		
		User user = userService.checkEmailAvailability(authentication.getName());
		
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("loggedInUserId", user.getUserId());
		formData.addAttribute("searchProduct", searchProduct);
		
		return "inventory";
	}
	
}
