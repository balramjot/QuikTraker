package org.balramjot.quiktraker.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.balramjot.quiktraker.models.ContactUs;
import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.services.UserContactUsSerive;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller that controls all the contact us messages related working for user
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class UserContactController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	UserContactUsSerive userContactUsService;
	
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
	 * @working to go to the contact us messages user page
	 * method initialize contact us form values and add them to form attributes
	 * @param formData to add form attributes
	 * @return html page
	 */
	@GetMapping("/contact")
	public String contactSupportPage(Model formData) {
		ContactUs contactUs = new ContactUs();
		formData.addAttribute("contactUs", contactUs);
		return "contact_support";
	}
	
	/**
	 * @POST method
	 * @working - to send contact us messages to administrator from user end
	 * method checks for errors first, if error exist shows respective error on page and stops further execution of code
	 * then, authenticate logged in user
	 * if authentication successful then, send the contact us message
	 * else, show error message and stop code execution
	 * @param contactUs holds contact us related entities
	 * @param result to bind values
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@PostMapping("/contact/sendContactMessage")
	public String saveContactUsMessage(@Valid @ModelAttribute("contactUs") ContactUs contactUs, BindingResult result, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		if(result.hasErrors()) {
				ArrayList<String> errorMessages = new ArrayList<String>();
				result.getAllErrors().stream().forEach(
						f -> { f.getDefaultMessage(); 
								errorMessages.add(f.getDefaultMessage());
								redirectAttributes.addFlashAttribute("errorMessage", errorMessages);
						});
				return "redirect:/contact";
			}
		  
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			
			User user = userService.checkEmailAvailability(authentication.getName());
			
			if(user != null) {
					
					contactUs.setUser(user);
					userContactUsService.save(contactUs);
					
					redirectAttributes.addFlashAttribute("message", "Your message has been sent successfully");
					redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			}
			else {
				return "redirect:/accessdenied";
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/contact";  
		
	}
}
