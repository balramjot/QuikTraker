package org.balramjot.quiktraker.controllers;

import java.util.List;

import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller that controls all the administrator users related working
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class AdminUsersController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	
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
	 * @working - go to users page for administrator
	 * redirect to the users administrator page
	 * with list of all the users
	 * @param formData to add form attributes
	 * @return name of the html page
	 */
	@GetMapping("/admin/users")
	public String managerUsersPage(Model formData) {
		List<User> allUsers = userService.showAllUsersOnly();
		formData.addAttribute("allUsers", allUsers);
		return "admin/admin_manage_users";
	}
	
	/**
	 * @GET method
	 * @working activate or deactivate users
	 * method authenticate logged in user
	 * if authentication successful then, check for users i.e. not null
	 * if user status is true then makes it false, otherwise vice-versa
	 * if user is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * at last, save the update record
	 * @param id user id
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@GetMapping("/admin/UsersStatus/{id}")
	public String activateDeactivateUser(@PathVariable("id") long id, Model formData, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			User user = userService.findUserById(id);
			if(user != null) {
				
				if(user.getEnabled() == true) {
					user.setEnabled(false);
				} else {
					user.setEnabled(true);
				}
				userService.save(user);
				
				redirectAttributes.addFlashAttribute("message", "User has been updated successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", "User not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/users";	
	}
	
	
}
