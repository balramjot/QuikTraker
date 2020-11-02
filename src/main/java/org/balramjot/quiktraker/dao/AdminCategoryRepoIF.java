package org.balramjot.quiktraker.dao;

import java.util.List;

import org.balramjot.quiktraker.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for product category service class
 * @author BjSaini
 *
 */
@Repository
public interface AdminCategoryRepoIF extends JpaRepository<ProductCategory, Long> {
	
	/**
	 * method to get list of all product categories sorted according to creation date time
	 * @return product category list
	 */
	List<ProductCategory> findAllByOrderByCreationDateTimeDesc();
	
	/**
	 * method to get list of all product categories where product category status is true and sorted according to creation date time
	 * @return product category list
	 */
	List<ProductCategory> findByCategoryStatusTrueOrderByCreationDateTimeDesc();
}
