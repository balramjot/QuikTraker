package org.balramjot.quiktraker.controllers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javax.validation.Valid;

import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.models.UserChangePassword;
import org.balramjot.quiktraker.models.UserInfo;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller that controls all the user profile related working
 * such as change password and edit profile
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class UserProfileController {
	
	/**
	 * Controller that controls all the product category related working
	 * @author BjSaini
	 * @version 1.0
	 */
	@Autowired
	UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	UserInfoService userInfoService;
	
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
	 * @working - go to change password page
	 * redirect to the change password page
	 * with initial change password attributes
	 * @param formData to add form attributes
	 * @return name of the html page
	 */
	@GetMapping("/privacy")
	public String changePasswordPage(Model formData) {
		UserChangePassword changePassword = new UserChangePassword();
		formData.addAttribute("changePassword", changePassword);
		return "change_password";
	}
	
	/**
	 * @POST
	 * @working update password for user
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * then, authenticate logged in user
	 * if authentication successful then,
	 * Check if database password matches with the current password that user just typed in
	 * if yes, update the password in the database with the new password
	 * if password not matches with the one in the database then, show error message
	 * else, show error message and stop code execution
	 * @param changePassword holds change password related entities
	 * @param result to bind values
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@PostMapping("/privacy/updatePassword")
	public String saveUser(@Valid @ModelAttribute("changePassword") UserChangePassword changePassword, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
		if(result.hasErrors()) {
			ArrayList<String> errorMessages = new ArrayList<String>();
			result.getAllErrors().stream().forEach(
					f -> { f.getDefaultMessage(); 
							errorMessages.add(f.getDefaultMessage());
							redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
					});
			return "redirect:/privacy";
		}
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			User user = userService.checkEmailAvailability(authentication.getName());	
			if(user != null) {
				if(passwordEncoder.matches(changePassword.getPassword(), user.getPassword())) {
					
					// update the password
					user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
					userService.save(user);
					
					redirectAttributes.addFlashAttribute("message", "Password has been updated successfully");
					redirectAttributes.addFlashAttribute("swapClass", "alert-success");
				} else {
					redirectAttributes.addFlashAttribute("message", "Wrong Password. Please check your password");
					redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
				}			
			}
			else {
				return "redirect:/accessdenied";
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/privacy";
	}
	
	/**
	 * @GET method
	 * @working - user edit profile page
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * then, authenticate logged in user
	 * if authentication successful then,
	 * checking if there is some data in the user info entity
	 * if yes, send data to the form
	 * otherwise, show error message
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @param redirectAttributes used for page redirection
	 * @return name of the html page
	 */
	@GetMapping("/profile")
	public String editProfilePage(Model formData, Authentication authentication, RedirectAttributes redirectAttributes) {		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			// retrieve the data from the logged in user
			User user = userService.checkEmailAvailability(authentication.getName());
			if(user != null) {
				if(userInfoService.checkUserDetailsExistorNotByUserId(user.getUserInfo().getUserInfoId()) == true) {
					// retrieve data from the userinfo table
					UserInfo userfet = userInfoService.getUserInformation(user.getUserInfo().getUserInfoId());
					if(userfet != null) {
						formData.addAttribute("userCreateDate", DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH).format(userfet.getCreationDateTime().toLocalDate()));
						formData.addAttribute("userInfo", userfet);
					} else {
						return "redirect:/accessdenied";
					}
				} else {
					return "redirect:/accessdenied";
				}
			} else {
				return "redirect:/accessdenied";
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "edit_profile";
	}
	
	
	/**
	 * @POST
	 * @working update user profile
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * then, authenticate logged in user
	 * if authentication successful then,
	 * getting user details from userinfo table using relationship between users and userinfo table
	 * if yes, update both users and userinfo table with required set of form values
	 * otherwise, show error message
	 * else, show error message and stop code execution
	 * @param userInfo holds user information related entities
	 * @param result to bind values
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@PostMapping("/profile/updateUser")
	public String updateUser(@Valid @ModelAttribute("userInfo") UserInfo userInfo, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
		   if(result.hasErrors()) {
			ArrayList<String> errorMessages = new ArrayList<String>();
			result.getAllErrors().stream().forEach(
					f -> { f.getDefaultMessage(); 
							errorMessages.add(f.getDefaultMessage());
							redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
					});
			return "redirect:/profile";
		}		
		   if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			   
			   // retrieve the data from the logged in user
				User user = userService.checkEmailAvailability(authentication.getName());
				
				if(user != null) {
					
					if(userInfoService.checkUserDetailsExistorNotByUserId(user.getUserInfo().getUserInfoId()) == true) {
						// retrieve data from the userinfo table using userinfoid frfom users table
						UserInfo userinfoget = userInfoService.getUserInformation(user.getUserInfo().getUserInfoId());
						
						if(userinfoget != null) {
							
							// setting first name and last name using form values
							user.setFirstName(userInfo.getUser().getFirstName());
							user.setLastName(userInfo.getUser().getLastName());
							
							// calling the save method from userservice class to update user
							userService.save(user);
				
							// setting user info details using form values
							userinfoget.setAddress(userInfo.getAddress());
							userinfoget.setCity(userInfo.getCity());
							userinfoget.setProvince(userInfo.getProvince());
							userinfoget.setZipcode(userInfo.getZipcode());
							userinfoget.setPhoneNo(userInfo.getPhoneNo());
							userinfoget.setProfilePic(userInfo.getProfilePic());
							userinfoget.setUser(user);
							
							// calling the save method from userinfoservice class to update user details
							userInfoService.save(userinfoget);
							
							// success message
							redirectAttributes.addFlashAttribute("message", "User Profile has been updated successfully");
							redirectAttributes.addFlashAttribute("swapClass", "alert-success");
						} else {
							return "redirect:/accessdenied";
						}
					} 
					else 
			        { 
						return "redirect:/accessdenied";
			        }
				} else {
					// error message
					redirectAttributes.addFlashAttribute("message", "Something went wrong. Please try again !!!");
					redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
				}
		   } else {
				return "redirect:/accessdenied";
			}
		// page redirection
		return "redirect:/profile";
	}
	
	
}
