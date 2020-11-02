package org.balramjot.quiktraker.dao;

import java.util.List;

import org.balramjot.quiktraker.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for product service class
 * @author BjSaini
 *
 */
@Repository
public interface AdminProductRepoIF extends JpaRepository<Product, Long> {
	
	/**
	 * method to get list of all products sorted according to creation date time
	 * @return product list
	 */
	List<Product> findAllByOrderByCreationDateTimeDesc();
	
	/**
	 * method to get list of all products where product status is true and sorted according to creation date time
	 * @return product list
	 */
	List<Product> findByProductStatusTrueOrderByCreationDateTimeDesc();
	
	/**
	 * method to get count of products those are critical i.e. where quantity in stock is less than 25% of the alloted quantity
	 * @return product count
	 */
	@Query("select COUNT(*) from Product p WHERE ROUND((p.inStock/p.allotedQuantity),2) < '0.25'")
	Long countByLowQuantity();
	
	/**
	 * method to get list of products those are critical i.e. where quantity in stock is less than 25% of the alloted quantity
	 * @return product list
	 */
	@Query("select p from Product p WHERE ROUND((p.inStock/p.allotedQuantity),2) < '0.25'")
	List<Product> findByLowQuanity();
	
	/**
	 * method to get count of in stock items
	 * @param b which is quantity in stock
	 * @return product count
	 */
	Long countByInStock(long b);
	
	/**
	 * method to get list of all products sorted according to quantity in stock ascending order
	 * @return product list
	 */
	List<Product> findAllByOrderByInStockAsc();
	
	/**
	 * method to get list of product categories and their count according to their categories
	 * @param pageable - limit records to fetch in return
	 * @return product list
	 */
	@Query("select new org.balramjot.quiktraker.models.Product(t.productCategory,COUNT(t.productId)) from Product AS t GROUP BY t.productCategory.categoryId ORDER BY COUNT(t.productId) DESC")
	List<Product> findByProductCategoryProductsTable(Pageable pageable);
	
	/**
	 * method to get list of all products where product status is true and sorted according to creation date time ascending order
	 * @return product list
	 */
	List<Product> findByProductStatusTrueOrderByCreationDateTimeAsc();
	
	/**
	 * method to get list of all products where product status is true and sorted according to quantity in stock descending order
	 * @return product list
	 */
	List<Product> findByProductStatusTrueOrderByInStockDesc();
	
	/**
	 * method to get list of all products where product status is true and sorted according to quantity in stock ascending order
	 * @return product list
	 */
	List<Product> findByProductStatusTrueOrderByInStockAsc();
	
	/**
	 * method to get list of the products searched according to keyword
	 * @param keyword - which takes input search parameter
	 * @return product list
	 */
	@Query("select p FROM Product p WHERE CONCAT(p.productName, ' ', p.productDescription, ' ', p.productCategory.categoryName) LIKE %?1%")
	List<Product> searchInventory(String keyword);
}
