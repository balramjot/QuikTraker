package org.balramjot.quiktraker.services;

import org.balramjot.quiktraker.dao.UserInfoRepoIF;
import org.balramjot.quiktraker.models.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for user information controller
 * @author BjSaini
 * @version 1.0
 */
@Service
public class UserInfoService {

	/**
	 * connecting the user information repository
	 */
	@Autowired
	UserInfoRepoIF userInfoRepoIf;
	
	/**
	 * method to save user information
	 * @param u which is user information
	 */
	public void save(UserInfo u) {
		userInfoRepoIf.save(u);
	}
	
	/**
	 * method to get user information from the database
	 * @param userId - user id
	 * @return product category if exist otherwise null
	 */
	public UserInfo getUserInformation(Long userId) {
		return userInfoRepoIf.findById(userId).get();
	}
	
	/**
	 * method to check if user information already exist in the database or not
	 * @param userId - user id
	 * @return boolean - true if user information already exists and false if not
	 */
	public boolean checkUserDetailsExistorNotByUserId(Long userId) {
		return userInfoRepoIf.findById(userId).isPresent();
	}
}
