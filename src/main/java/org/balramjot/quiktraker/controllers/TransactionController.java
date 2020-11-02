package org.balramjot.quiktraker.controllers;

import java.util.List;

import org.balramjot.quiktraker.models.Product;
import org.balramjot.quiktraker.models.Transaction;
import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.services.AdminProductService;
import org.balramjot.quiktraker.services.TransactionService;
import org.balramjot.quiktraker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Controller that controls all the tranasctions related working
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class TransactionController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
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
	 * @GET method ( for administrator )
	 * @working - go to transactions page that shows transactions records according to products
	 * methods add the products list and user details to the form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return name of the html page
	 */
	@GetMapping("/admin/transactionProduct")
	public String transactionProductPage(Model formData, Authentication authentication) {
		List<Product> allProduct = adminProductService.showAll();
		User user = userService.checkEmailAvailability(authentication.getName());
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("adminDetail", user.getEmail());
		
		return "admin/admin_transaction_byproduct";
	}
	
	/**
	 * @GET method ( for administrator )
	 * @working - go to transactions page that shows transactions records according to users
	 * methods add the users list to the form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return name of the html page
	 */
	@GetMapping("/admin/transactionUser")
	public String transactionUserPage(Model formData, Authentication authentication) {
		List<User> allUsers = userService.showAllUsersOnly();
		formData.addAttribute("allUsers", allUsers);
		
		return "admin/admin_transaction_byuser";
	}
	
	/**
	 * @GET method ( for users )
	 * @working - go to transactions page that shows transactions records
	 * methods add the products list and user details to the form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return name of the html page
	 */
	@GetMapping("/user/transactions")
	public String userAllTransactionPage(Model formData, Authentication authentication) {
		List<Product> allProduct = adminProductService.showAll();
		User user = userService.checkEmailAvailability(authentication.getName());
		formData.addAttribute("allProduct", allProduct);
		formData.addAttribute("adminDetail", user.getEmail());
		
		return "transactions";
	}
	
	/**
	 * @GET method ( for users )
	 * @working - go to transactions page that shows transactions records for particular logged in user
	 * methods add the transactions list and transactions count to the form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return name of the html page
	 */
	@GetMapping("/user/myTransaction")
	public String userMyTransactionPage(Model formData, Authentication authentication) {
		User user = userService.checkEmailAvailability(authentication.getName());
		List<Transaction> allTransactions = transactionService.showAllTransactionsUserAccTime(user.getUserId());
		Long countmyTransaction = transactionService.countTransactionbyUserId(user.getUserId());
		formData.addAttribute("allTransactions", allTransactions);
		formData.addAttribute("countmyTransaction", countmyTransaction);
		
		return "myTransaction";
	}
	
}
