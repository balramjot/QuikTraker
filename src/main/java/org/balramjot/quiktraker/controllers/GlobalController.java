package org.balramjot.quiktraker.controllers;

import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.models.UserInfo;
import org.balramjot.quiktraker.services.FavouriteService;
import org.balramjot.quiktraker.services.UserInfoService;
import org.balramjot.quiktraker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * global controller for user end
 * @author BjSaini
 * @version 1.0
 */
@ControllerAdvice
public class GlobalController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	FavouriteService favouriteService;
	
	/**
	 * @working to show navigation bar global values and
	 * other user related global dynamic values in the user end
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @param redirectAttributes used for page redirection
	 * @return null
	 */
	@ModelAttribute("globalLoggedInUserDetails")
	public String globalUserDetails(Model formData, Authentication authentication, RedirectAttributes redirectAttributes) {
		
		try {
			authentication.isAuthenticated();
			User user = userService.checkEmailAvailability(authentication.getName());
			UserInfo userfet = userInfoService.getUserInformation(user.getUserInfo().getUserInfoId());
			Long countFav = favouriteService.countFavouritebyUserId(user.getUserId());
			formData.addAttribute("globalLoggedInUserDetails", userfet.getProfilePic());
			formData.addAttribute("globalLoggedInUserEmpId", "#QT-"+user.getUserId());
			formData.addAttribute("globalFavouriteProductUser", countFav);
			return null;
		} catch (NullPointerException e) {
			return "redirect:/";
		}
		
	}
}
