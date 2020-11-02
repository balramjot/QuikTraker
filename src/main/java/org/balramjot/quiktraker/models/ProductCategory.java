package org.balramjot.quiktraker.models;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
/**
 * Model to hold product categories data
 * Used with JPA to add entity and model to the database
 * Jpa validations used are :
 * @NotNull - field must be filled
 * @author BjSaini
 * @version 1.0
 */
@Entity
@Table(name="productcategory")
public class ProductCategory {
	
	/*
	 * Entities
	 * @categoryId - category id for the product category
	 * @categoryName - category name for the product category
	 * @creationDateTime - insertion date time which is current time by default
	 * @categoryStatus - category status which is true by default
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long categoryId;
	@Basic
	@NotNull(message="Please enter category name")
	private String categoryName;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime creationDateTime;
	@Column(columnDefinition = "boolean default true")
	private Boolean categoryStatus = true;
	
	/**
	 * Default constructor
	 */
	public ProductCategory() {
		super();
	}
	
	/*
	 * getters and setters according to entities
	 */
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public Boolean getCategoryStatus() {
		return categoryStatus;
	}
	public void setCategoryStatus(Boolean categoryStatus) {
		this.categoryStatus = categoryStatus;
	}
		
}
