package org.balramjot.quiktraker.models;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Model to hold favorites data
 * Used with JPA to add entity and model to the database
 * Holding many to one relationship with the user class
 * Holding many to one relationship with the product class
 * @author BjSaini
 * @version 1.0
 */

@Entity
@Table(name="favourites")
public class Favourites {
	
	/*
	 * Entities
	 * @favouriteId - favorite id for the favorite products
	 * @favouriteStatus - favorite product status which is true by default
	 * @creationDateTime - insertion date time for the favorite product which is current time by default
	 * @user - many to one relationship with the users
	 * @product - many to one relationship with the products
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long favouriteId;
	@Basic
	@Column(columnDefinition = "boolean default true")
	private Boolean favouriteStatus = true;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime creationDateTime;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	

	/**
	 * Default constructor
	 */
	public Favourites() { }
	
	/**
	 * Argument constructor
	 */
	public Favourites(User user, Product product) {
		super();
		this.user = user;
		this.product = product;
	}

	/*
	 * getters and setters according to entities
	 */
	public Long getFavouriteId() {
		return favouriteId;
	}
	public void setFavouriteId(Long favouriteId) {
		this.favouriteId = favouriteId;
	}
	public Boolean getFavouriteStatus() {
		return favouriteStatus;
	}
	public void setFavouriteStatus(Boolean favouriteStatus) {
		this.favouriteStatus = favouriteStatus;
	}
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
