package org.balramjot.quiktraker.dao;


import java.util.List;

import org.balramjot.quiktraker.models.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for favorite service class
 * @author BjSaini
 *
 */
@Repository
public interface FavouritesRepoIF extends JpaRepository<Favourites, Long> {
	
	/**
	 * method to check if their is record of the product saved as favorite in database
	 * according to particular product and user id
	 * @param productId which is the id of the product
	 * @param userId which is the id of the logged in user
	 * @return true if product exist as favorite and false if not
	 */
	Boolean existsByProductProductIdAndUserUserId(Long productId, Long userId);
	
	/**
	 * method to get record of the product saved as favorite in database
	 * according to particular product and user id
	 * @param productId which is the id of the product
	 * @param userId which is the id of the logged in user
	 * @return particular product record which is present in table
	 */
	Favourites findByProductProductIdAndUserUserId(Long productId, Long userId);
	
	/**
	 * method to count of the products that are added as favorites by the logged in user
	 * @param userId which is the id of the logged in user
	 * @return count of products that are added as favorite
	 */
	Long countByUserUserId(Long userId);
	
	/**
	 * method to get list of all products that are added as favorite by the particular user
	 * sorted according to creation date time
	 * @param userId which is the id of the logged in user
	 * @return list of products that are added as favorite
	 */
	List<Favourites> findByUserUserIdOrderByCreationDateTimeDesc(Long userId);
	
}
