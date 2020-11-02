package org.balramjot.quiktraker.controllers;

import java.util.List;

import org.balramjot.quiktraker.models.ContactUs;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller that controls all the contact us messages related working
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class AdminContactUsController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private UserContactUsSerive userContactUsService;
	
	/**
	 * getters and setters
	 */
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public UserContactUsSerive getUserContactUsService() {
		return userContactUsService;
	}
	public void setUserContactUsService(UserContactUsSerive userContactUsService) {
		this.userContactUsService = userContactUsService;
	}
	
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
	 * @working - go to contact us messages page for administrator
	 * redirect to the contact us messages page for administrator
	 * with contact us messages list and initial contact us attributes
	 * @param formData to add form attributes
	 * @return name of the html page
	 */
	@GetMapping("/admin/contactmessages")
	public String contactUsMessages(Model formData) {
		List<ContactUs> contactUs = userContactUsService.showAllMessages();
		ContactUs iniContact = new ContactUs();
		formData.addAttribute("contactUs", contactUs);
		formData.addAttribute("iniContact", iniContact);
		return "admin/admin_contactus_messages";
	}
	
	/**
	 * @GET method
	 * @working delete contact us messages from database
	 * method authenticate logged in user
	 * if authentication successful then, check for contact us class i.e. not null
	 * delete contact us message
	 * if contact us is null then, show error message
	 * if authentication unsuccessful, show error message and stop code execution 
	 * @param id contact us id
	 * @param formData to add form attributes
	 * @param redirectAttributes used for page redirection
	 * @param authentication for user authentication
	 * @return method mapping
	 */
	@GetMapping("/admin/contactmessages/{id}")
	public String removeMessage(@PathVariable("id") long id, Model formData, RedirectAttributes redirectAttributes, Authentication authentication) {
		
		if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
			ContactUs contactUs = userContactUsService.findMessagebyId(id);
			if(contactUs != null) {
				userContactUsService.deleteMessageById(id);
				
				redirectAttributes.addFlashAttribute("message", "Message has been deleted successfully");
				redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			} else {
				redirectAttributes.addFlashAttribute("message", "Message not exist");
				redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
			}
		} else {
			return "redirect:/accessdenied";
		}
		return "redirect:/admin/contactmessages";	
	}
	
	/**
	 * @POST method
	 * @working mark messages as read
	 * method checks if messages to be read checklist is not empty,
	 * then update status of contact us messages in database to true
	 * and save changes to the database
	 * @param result to bind values
	 * @param redirectAttributes used for page redirection
	 * @param contactUs holds contact us related entities
	 * @return method mapping
	 */
	@PostMapping("/admin/readAllMessages")
	public String readAllContactMessages(@ModelAttribute("iniContact") ContactUs contactUs, BindingResult result, RedirectAttributes redirectAttributes) {
				
		if(!contactUs.getMarkRead().isEmpty()) {
			contactUs.getMarkRead().forEach(f -> {
				ContactUs contactUsFet = userContactUsService.findMessagebyId(f);
				contactUsFet.setContactStatus(true);
				userContactUsService.save(contactUsFet);
			});
			
			redirectAttributes.addFlashAttribute("message", "Selected message(s) have been mark as read");
			redirectAttributes.addFlashAttribute("swapClass", "alert-success");
			
		} else {
			redirectAttributes.addFlashAttribute("message", "Please select at least one message to mark it as read");
			redirectAttributes.addFlashAttribute("swapClass", "alert-danger");
		}
		
		return "redirect:/admin/contactmessages";
	}
	
	/**
	 * @GET method
	 * @working show unread messages first followed by read messages
	 * method get the list of all the messages sorted according to unread messages first and then read messages
	 * add list and initial contact us attributes to the form
	 * @param formData to add form attributes
	 * @return html page
	 */
	@GetMapping("/admin/unreadcontactmessages")
	public String unreadContactUsMessages(Model formData) {
		List<ContactUs> contactUs = userContactUsService.showUnreadMessages();
		ContactUs iniContact = new ContactUs();
		formData.addAttribute("contactUs", contactUs);
		formData.addAttribute("iniContact", iniContact);
		return "admin/admin_contactus_messages";
	}
	
	
}
