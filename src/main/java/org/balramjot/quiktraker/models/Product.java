package org.balramjot.quiktraker.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.balramjot.quiktraker.annotations.ValidStockQuantity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Model to hold products data
 * Used with JPA to add entity and model to the database
 * Jpa validations used are :
 * @NotNull - field must be filled
 * @ValidStockQuantity - to check if stock quantity to be added should be equal to less than alloted quantity
 * Holding many to one relationship with the product category class
 * Holding one to many relationship with the transaction class
 * Holding one to many relationship with the favorites class
 * @author BjSaini
 * @version 1.0
 */
@Entity
@Table(name="products")
@ValidStockQuantity
public class Product {
	
	/*
	 * Entities
	 * @productId - product id for the products
	 * @productName - product name for the products
	 * @productDescription - product description for the products
	 * @inStock - in stock quantity for the product
	 * @allotedQuantity - quantity to be alloted to the product
	 * @productImage - image for the product
	 * @creationDateTime - insertion date time for the product which is current time by default
	 * @productStatus - product status which is true by default
	 * @productCategory - many to one relationship with the product category
	 * @transactionList - one to many relationship with the transactions
	 * @favouritesList - one to many relationship with the favorites products
	 * @cnt - used as a counter to count favorites products
	 * @searchParam - for product search
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long productId;
	@Basic
	@NotNull(message="Please enter product name")
	private String productName;
	@Lob
	private String productDescription;
	@NotNull(message="Please enter quantity")
	private Long inStock;
	@NotNull(message="Please enter alloted quantity")
	private Long allotedQuantity;
	@Lob
	private String productImage;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime creationDateTime;
	@Column(columnDefinition = "boolean default true")
	private Boolean productStatus = true;
	@ManyToOne
	@JoinColumn(name="productCategory_id")
	private ProductCategory productCategory;
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Transaction> transactionList;
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Favourites> favouritesList;
	@Transient																			// ignore field creation in database
	private Long cnt;																
	@Transient																			// ignore field creation in database
	private String searchParam;														
	
	/**
	 * Default constructor
	 */
	public Product() { }
	
	/**
	 * Argument constructor
	 */
	public Product(String productName, String productDescription, Long inStock, Long allotedQuantity, ProductCategory productCategory, List<Transaction> transactionList, List<Favourites> favouritesList) {
		super();
		this.productName = productName;
		this.productDescription = productDescription;
		this.inStock = inStock;
		this.allotedQuantity = allotedQuantity;
		this.productCategory = productCategory;
		this.transactionList = transactionList;
		this.favouritesList = favouritesList;
	}
	
	/**
	 * Two Argument constructor
	 */
	public Product(ProductCategory productCategory, Long cnt) {
		super();
		this.productCategory = productCategory;
		this.cnt = cnt;
	}

	/*
	 * getters and setters according to entities
	 */
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public Long getInStock() {
		return inStock;
	}
	public void setInStock(Long inStock) {
		this.inStock = inStock;
	}
	public Long getAllotedQuantity() {
		return allotedQuantity;
	}
	public void setAllotedQuantity(Long allotedQuantity) {
		this.allotedQuantity = allotedQuantity;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public Boolean getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(Boolean productStatus) {
		this.productStatus = productStatus;
	}
	public ProductCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}
	public List<Transaction> getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}
	public List<Favourites> getFavouritesList() {
		return favouritesList;
	}
	public void setFavouritesList(List<Favourites> favouritesList) {
		this.favouritesList = favouritesList;
	}
	public Long getCnt() {
		return cnt;
	}
	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	public String getSearchParam() {
		return searchParam;
	}
	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}
	
}
