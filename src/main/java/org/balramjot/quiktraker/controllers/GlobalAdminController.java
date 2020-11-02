package org.balramjot.quiktraker.controllers;

import java.util.List;

import org.balramjot.quiktraker.models.ContactUs;
import org.balramjot.quiktraker.models.Product;
import org.balramjot.quiktraker.services.AdminProductService;
import org.balramjot.quiktraker.services.UserContactUsSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * global controller for administrator end
 * @author BjSaini
 * @version 1.0
 */
@ControllerAdvice
public class GlobalAdminController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserContactUsSerive userContactUsService;
	@Autowired
	AdminProductService adminProductService;
	
	/**
	 * @working to show values for low stock products and
	 * unread contact us messages in the header of the administrator end
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @param redirectAttributes used for page redirection
	 * @return null
	 */
	@ModelAttribute("unreadContactMessages")
	public String globalUserDetails(Model formData, Authentication authentication, RedirectAttributes redirectAttributes) {
		try {
			authentication.isAuthenticated();
			Long countunreadMessages = userContactUsService.countUnreadMessages(false);
			List<ContactUs> unreadMessagesList = userContactUsService.showUnreadAllMessages(false);
			
			Long countLowStockProduct = adminProductService.countAllLowQuantity();
			List<Product> lowStockProduct = adminProductService.showAllLowQuantity();
			
			formData.addAttribute("unreadContactMessages", countunreadMessages);
			formData.addAttribute("unreadMessagesList", unreadMessagesList);
			formData.addAttribute("countLowStockProduct", countLowStockProduct);
			formData.addAttribute("lowStockProductList", lowStockProduct);
			return null;
		} catch (NullPointerException e) {
			return "redirect:/";
		}
		
	}
}
