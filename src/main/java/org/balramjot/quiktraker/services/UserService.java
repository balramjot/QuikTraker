package org.balramjot.quiktraker.services;

import java.util.List;

import org.balramjot.quiktraker.dao.UserRepoIF;
import org.balramjot.quiktraker.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for user controller
 * @author BjSaini
 * @version 1.0
 */
@Service
public class UserService {
	
	/**
	 * connecting the product category repository
	 */
	@Autowired
	UserRepoIF userRepoIf;
	
	/**
	 * method to save user
	 * @param u which is user
	 */
	public void save(User u) {
		userRepoIf.save(u);
	}
	
	/**
	 * method to get user data according to email address
	 * @param email which is the email of the user
	 * @return user data
	 */
	public User checkEmailAvailability(String email) {
		return userRepoIf.findByEmail(email).get();
	}
	
	/**
	 * method to check if particular user exist in database or not
	 * @param email which is the email of the user
	 * @return true if user exist and false if not
	 */
	public boolean checkUserExistorNotByEmail(String email) {
		return userRepoIf.findByEmail(email).isPresent();
	}
	
	/**
	 * method to get list of the users according to their role
	 * and sorted according to creation date time descending order
	 * @param role which is the role to be count
	 * @return list of the users
	 */
	public List<User> showAllUsersOnly() {
		return userRepoIf.findByRoleOrderByCreationDateTimeDesc("ROLE_USER");
	}
	
	/**
	 * method to check if user exist in the database or not
	 * @param id - user id
	 * @return boolean - true if user exists and false if not
	 */
	public boolean existById(long id) {
		return userRepoIf.existsById(id);
	}
	
	/**
	 * method to get list of user if exist in the database
	 * @param id - user id
	 * @return user if exist otherwise null
	 */
	public User findUserById(long id) {
		if(existById(id))
			return userRepoIf.findById(id).get();
		else
			return null;
	}
	
	/**
	 * method to get count of the users according to their role
	 * @param role which is the role to be count
	 * @return long which is the count according to role
	 */
	public Long getTotalUsers() {
		return userRepoIf.countByRole("ROLE_USER");
	}
	
	/**
	 * method to check if admin already has an account or not
	 * @param role which is the role of the admin
	 * @return true if admin exist in database and false if not
	 */
	public boolean getAdminExistorNot(String role) {
		if(userRepoIf.countByRole(role) >= 1)
			return true;						// admin exists
		else
			return false;						// admin not exists
	}
	
}
