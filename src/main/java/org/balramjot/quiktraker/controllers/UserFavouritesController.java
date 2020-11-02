package org.balramjot.quiktraker.controllers;

import java.util.List;

import org.balramjot.quiktraker.models.Favourites;
import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.services.AdminProductService;
import org.balramjot.quiktraker.services.FavouriteService;
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
 * Controller that controls all the favorites related working
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class UserFavouritesController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	AdminProductService adminProductService;
	@Autowired
	FavouriteService favouritesService;
	
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
	 * @working to get favorites products list
	 * method get the list of all the favorites products added in the logged in user list
	 * and add them to form attributes
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/favorite")
	public String favoritePage(Model formData, Authentication authentication) {
		User user = userService.checkEmailAvailability(authentication.getName());
		List<Favourites> allFavourites = favouritesService.showAllFavouritesUserAccTime(user.getUserId());
		
		formData.addAttribute("allFavourites", allFavourites);
		return "favorite";
	}
	
}
