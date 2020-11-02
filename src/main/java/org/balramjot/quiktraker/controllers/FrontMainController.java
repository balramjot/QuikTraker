package org.balramjot.quiktraker.controllers;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.balramjot.quiktraker.models.Favourites;
import org.balramjot.quiktraker.models.Transaction;
import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.models.UserInfo;
import org.balramjot.quiktraker.security.CheckUserLogInOrNot;
import org.balramjot.quiktraker.services.AdminProductService;
import org.balramjot.quiktraker.services.FavouriteService;
import org.balramjot.quiktraker.services.TransactionService;
import org.balramjot.quiktraker.services.UserInfoService;
import org.balramjot.quiktraker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller that controls all the before login controls of the web site
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class FrontMainController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	CheckUserLogInOrNot userStatus;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	AdminProductService adminProductService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	FavouriteService favouritesService;
	
	/**
	 * Initial method
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
//		binder.setDisallowedFields(new String[] {"password"});
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * @GET
	 * @working go to home page
	 * method allow user to this page only if not logged in
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @return html page name
	 */
	@GetMapping("/")
	public String indexPage(Model formData, RedirectAttributes redirectAttributes) {
		if(userStatus.isLogged() == false) {		
			return "index";
		}	
		else {
			return "redirect:/loginSuccess";
		}
	}
	
	/**
	 * @GET
	 * @working sign in and sign up page
	 * method restricting user to access login page 
	 * if already logged in go to dashboard page according to user role
	 * otherwise, go to the sing in/sign up page
	 * This method also add another form attribute to the form according to the key that if
	 * there is record under the role of ADMIN in the database
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @return html page name
	 */
	@GetMapping("/signIn")
	public String singInPage(Model formData, RedirectAttributes redirectAttributes) {
		User newUser = new User();
		formData.addAttribute("newUser", newUser);
		
		if(userStatus.isLogged() == false) {		
			if(userService.getAdminExistorNot("ROLE_ADMIN") == true) {
				formData.addAttribute("roleExistance", "true");
			} else {
				formData.addAttribute("roleExistance", "false");
			}
			return "home_frontend";
		}	
		else {
			return "redirect:/loginSuccess";
		}
	}
	
	/**
	 * @GET
	 * @working to handle error during sign in
	 * method adds error parameter to the url and
	 * add error flash message and class to the page in case of error
	 * @param lg_error is url parameter
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @return method mapping
	 */
	@GetMapping("/loginError")
	public String loginError(@RequestParam(required = false, value="lg_error") String lg_error, Model formData, RedirectAttributes redirectAttributes) {
		User newUser = new User();
		formData.addAttribute("newUser", newUser);
		redirectAttributes.addFlashAttribute("message", "Invalid login. Please check your login credientials. If problem persist, please contact ADMINISTRATOR as your account may be locked");
		redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
		return "redirect:/signIn";
	}
	
	/**
	 * @GET
	 * @working redirect to successful login page
	 * method redirects logged in user according to their roles
	 * if user is admin, go to admin dashboard
	 * otherwise, go to user dashboard
	 * @param redirectAttributes used for page redirection
	 * @return method mapping
	 */
	@GetMapping("/loginSuccess")
	public String loginSuccess(RedirectAttributes redirectAttributes) {		
		if(userStatus.hasRole("ROLE_ADMIN"))
			return "redirect:/adminDashboard";
		else
			return "redirect:/userDashboard";
	}
	
	/**
	 * @POST
	 * @working - to register a new user
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * then, checking if email already exists in database or not
	 * if exists, give error message and do not allow to create new user
	 * otherwise, create a new user with success message
	 * @param newUser holds user related entities
	 * @param result to bind values
	 * @param redirectAttributes used for page redirection
	 * @return method mapping
	 */
	@PostMapping("/User/createUser")
	public String saveUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			ArrayList<String> errorMessages = new ArrayList<String>();
			result.getAllErrors().stream().forEach(
					f -> { 
						errorMessages.add(f.getDefaultMessage());
							redirectAttributes.addFlashAttribute("errorMessage", errorMessages);	
							System.out.println(f.getDefaultMessage());
					});
			return "redirect:/signIn";
		}		
			if(userService.checkUserExistorNotByEmail(newUser.getEmail()) == true) {
				redirectAttributes.addFlashAttribute("message", "User with same email address already exists");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			} else {
				// encoding password
				newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
				
				// instantiating the userinfo class
				UserInfo userinfo = new UserInfo();
				userinfo.setUser(newUser);
				
				// creating a new record in userinfo table
				userInfoService.save(userinfo);
				
				// setting the value for userinfo entity so that it can fill the join column attribute foreign key
				newUser.setUserInfo(userinfo);
				
				// saving entry in the users table
				userService.save(newUser);
				
				
				redirectAttributes.addFlashAttribute("message", "User has been created successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			}		 
		
		
		return "redirect:/signIn";
	}
	
	/********** user dashboard page ***********/
	/**
	 * @GET
	 * @working filling your dashboard with dynamic data
	 * methods uses different services to get the dynamic database results and
	 * show them in graphs and tables
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/userDashboard")
	public String userDashboard(Model formData, Authentication authentication) {
		User user = userService.checkEmailAvailability(authentication.getName());
		Long countmyTransaction = transactionService.countTransactionbyUserId(user.getUserId());
		Long productsCnt = adminProductService.countAllProducts();
		Long productsOutOfStockCnt = adminProductService.countAllOutOfStockProducts();
		Long productsCriticalCnt = adminProductService.countAllLowQuantity();
		List<Transaction> mostTransactionByProduct = transactionService.showMostTransactionsProduct();
		List<Favourites> myFavouritesList = favouritesService.showAllFavouritesUserAccTime(user.getUserId());
		List<Transaction> transactionPerMonthByUser = transactionService.showTransactionPerMonthByUser(user.getUserId());
		Map<String, Long> barChartMap = new LinkedHashMap<>();
		
		transactionPerMonthByUser.forEach(t-> {
			String monthString = new DateFormatSymbols().getMonths()[t.getMonthNum()-1];
			barChartMap.put(monthString, t.getCnt());
		});
		
		formData.addAttribute("countmyTransaction", countmyTransaction);
		formData.addAttribute("productsCnt", productsCnt);
		formData.addAttribute("productsCriticalCnt", productsCriticalCnt);
		formData.addAttribute("productsOutOfStockCnt", productsOutOfStockCnt);
		formData.addAttribute("mostTransactionByProduct", mostTransactionByProduct);
		formData.addAttribute("myFavouritesList", myFavouritesList);
		formData.addAttribute("barChartMap", barChartMap);
		return "dashboard";
	}
	
	/**
	 * @GET
	 * @working access denied page
	 * @return html page
	 */
	@GetMapping("/accessdenied")
	public String accessDenied() {
		return "error/something_went_wrong";
	}

}
