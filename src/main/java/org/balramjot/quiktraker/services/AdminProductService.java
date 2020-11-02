package org.balramjot.quiktraker.services;

import java.util.List;

import org.balramjot.quiktraker.dao.AdminProductRepoIF;
import org.balramjot.quiktraker.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service for product controller
 * @author BjSaini
 * @version 1.0
 */
@Service
public class AdminProductService {
	
	/**
	 * connecting the product repository
	 */
	@Autowired
	AdminProductRepoIF adminProductRepoIF;
	
	/**
	 * method to save a new product in the database
	 * @param u which is product
	 */
	public void save(Product u) {
		adminProductRepoIF.save(u);
	}
	
	/**
	 * method to get list of all products sorted according to creation date time
	 * @return list of products
	 */
	public List<Product> showAll() {
		return adminProductRepoIF.findAllByOrderByCreationDateTimeDesc();
	}
	
	/**
	 * method to check if product exist in the database or not
	 * @param id - product id
	 * @return boolean - true if product exists and false if not
	 */
	public boolean existById(long id) {
		return adminProductRepoIF.existsById(id);
	}
	
	/**
	 * method to get list of product  if exist in the database
	 * @param id - product id
	 * @return product if exist otherwise null
	 */
	public Product findProductById(long id) {
		if(existById(id))
			return adminProductRepoIF.findById(id).get();
		else
			return null;
	}
	
	/**
	 * method to delete product if exist in the database
	 * @param id - product id
	 */
	public void deleteProductById(long id) {
		if(existById(id))
			adminProductRepoIF.deleteById(id);
	}
	
	/**
	 * method to get list of all products where product status is true and sorted according to creation date time
	 * @return list of products
	 */
	public List<Product> showAllByStatus() {
		return adminProductRepoIF.findByProductStatusTrueOrderByCreationDateTimeDesc();
	}
	
	/**
	 * method to get list of products those are critical i.e. where quantity in stock is less than 25% of the alloted quantity
	 * @return list of products
	 */
	public List<Product> showAllLowQuantity() {
		return adminProductRepoIF.findByLowQuanity();
	}
	
	/**
	 * method to get count of products those are critical i.e. where quantity in stock is less than 25% of the alloted quantity
	 * @return count of low quantity products
	 */
	public Long countAllLowQuantity() {
		return adminProductRepoIF.countByLowQuantity();
	}
	
	/**
	 * method to get count of all products in the database
	 * @return count of all the products
	 */
	public Long countAllProducts() {
		return adminProductRepoIF.count();
	}
	
	/**
	 * method to get count of in stock items
	 * @param 0 to match in stock quantity
	 * @return product count
	 */
	public Long countAllOutOfStockProducts() {
		return adminProductRepoIF.countByInStock(0);
	}
	
	/**
	 * method to get list of all products sorted according to quantity in stock ascending order
	 * @return product list
	 */
	public List<Product> showAllOutOfStock() {
		return adminProductRepoIF.findAllByOrderByInStockAsc();
	}
	
	/**
	 * method to get list of product categories and their count according to their categories
	 * @param limitPage - limit records to fetch in return
	 * @return product list
	 */
	Pageable limitPage = PageRequest.of(0,7);
	public List<Product> showAllProductCategoryProducts() {
		return adminProductRepoIF.findByProductCategoryProductsTable(limitPage);
	}
	
	/**
	 * method to get list of all products where product status is true and sorted according to creation date time ascending order
	 * @return products list
	 */
	public List<Product> showAllOldestProduct() {
		return adminProductRepoIF.findByProductStatusTrueOrderByCreationDateTimeAsc();
	}
	
	/**
	 * method to get list of all products where product status is true and sorted according to quantity in stock ascending order
	 * @return products list
	 */
	public List<Product> showAllLowToHighProduct() {
		return adminProductRepoIF.findByProductStatusTrueOrderByInStockAsc();
	}
	
	/**
	 * method to get list of all products where product status is true and sorted according to quantity in stock descending order
	 * @return products list
	 */
	public List<Product> showAllHighToLowProduct() {
		return adminProductRepoIF.findByProductStatusTrueOrderByInStockDesc();
	}
	
	/**
	 * method to get list of the products searched according to keyword
	 * @param - keyword which takes input search parameter
	 * @return products list
	 */
	public List<Product> searchProduct(String keyword) {
		return adminProductRepoIF.searchInventory(keyword);
	}
}
