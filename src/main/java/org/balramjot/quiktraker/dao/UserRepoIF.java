package org.balramjot.quiktraker.dao;

import java.util.List;
import java.util.Optional;

import org.balramjot.quiktraker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for user service class
 * @author BjSaini
 *
 */
@Repository
public interface UserRepoIF extends JpaRepository<User, Long>{
	
	/**
	 * method to get list of the users according to email address
	 * @param email which is the email of the user
	 * @return list of the users
	 */
	Optional<User> findByEmail(String email);
	
	/**
	 * method to get list of the users according to their role
	 * and sorted according to creation date time descending order
	 * @param role which is the role to be count
	 * @return list of the users
	 */
	List<User> findByRoleOrderByCreationDateTimeDesc(String role);
	
	/**
	 * method to get count of the users according to their role
	 * @param role which is the role to be count
	 * @return long which is the count according to role
	 */
	Long countByRole(String role);
	
}
