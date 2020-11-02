package org.balramjot.quiktraker.services;

import java.util.List;

import org.balramjot.quiktraker.dao.FavouritesRepoIF;
import org.balramjot.quiktraker.models.Favourites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for favorite controller
 * @author BjSaini
 * @version 1.0
 */
@Service
public class FavouriteService {
	
	/**
	 * connecting the favorite product repository
	 */
	@Autowired
	FavouritesRepoIF favouritesRepoIf;
	
	/**
	 * method to save favorite product
	 * @param u which is favorite product
	 */
	public void save(Favourites u) {
		favouritesRepoIf.save(u);
	}
	
	/**
	 * method to check if favorite exist in the database or not
	 * @param id - category id
	 * @return boolean - true if favorite exists and false if not
	 */
	public boolean existById(long id) {
		return favouritesRepoIf.existsById(id);
	}
	
	/**
	 * method to get list of favorite if exist in the database
	 * @param id - favorite id
	 * @return favorite list if exist otherwise null
	 */
	public Favourites findFavouritebyId(long id) {
		if(existById(id))
			return favouritesRepoIf.findById(id).get();
		else
			return null;
	}
	
	/**
	 * method to delete favorite if exist in the database
	 * @param id - favorite id
	 */
	public void deleteFavouriteById(long id) {
		if(existById(id))
			favouritesRepoIf.deleteById(id);
	}
	
	/**
	 * method to check if their is record of the product saved as favorite in database
	 * according to particular product and user id
	 * @param productId which is the id of the product
	 * @param userId which is the id of the logged in user
	 * @return true if product exist as favorite and false if not
	 */
	public boolean existFavouriteByProductIdUserId(long productId, Long userId) {
		return favouritesRepoIf.existsByProductProductIdAndUserUserId(productId, userId);
	}
	
	/**
	 * method to get favorite product saved in the database
	 * @param productId which is the id of the product
	 * @param userId which is the id of the logged in user
	 * @return favorite product data if exist and false if not
	 */
	public Favourites findFavouritebyProductIdUserId(long productId, Long userId) {
		if(existFavouriteByProductIdUserId(productId, userId))
			return favouritesRepoIf.findByProductProductIdAndUserUserId(productId, userId);
		else
			return null;
	}
	
	/**
	 * method to count of the products that are added as favorites by the logged in user
	 * @param userId which is the id of the logged in user
	 * @return count of products that are added as favorite
	 */
	public Long countFavouritebyUserId(Long userId) {
		return favouritesRepoIf.countByUserUserId(userId);
	}
	
	/**
	 * method to get list of all products that are added as favorite by the particular user
	 * sorted according to creation date time
	 * @param userId which is the id of the logged in user
	 * @return list of products that are added as favorite
	 */
	public List<Favourites> showAllFavouritesUserAccTime(Long userId) {
		return favouritesRepoIf.findByUserUserIdOrderByCreationDateTimeDesc(userId);
	}
	
}
