package org.balramjot.quiktraker.models;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

/**
 * Model to hold transactions data
 * Used with JPA to add entity and model to the database
 * Jpa validations used are :
 * @NotNull - field must be filled
 * Holding many to one relationship with the user class
 * Holding many to one relationship with the product class
 * @author BjSaini
 * @version 1.0
 */
@Entity
@Table(name="transactions")
public class Transaction {
	
	/*
	 * Entities
	 * @transactionId - transaction id for the transaction
	 * @quantity - quantity deposited or withdrawn from the inventory by the user
	 * @quantityRemaining - quantity remaining at in stock at the time of transaction
	 * @transactionType - type of transaction made by user whether deposit or withdraw
	 * @comment - comment for the transaction
	 * @transactionStatus - transaction status which is true by default
	 * @creationDateTime - insertion date time for the transaction which is current time by default
	 * @user - many to one relationship with the users
	 * @product - many to one relationship with the products
	 * @cnt - used as a counter to count transactions
	 * @monthNum - to get month number out of whole creationDateTime
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long transactionId;
	@Basic
	@NotNull(message="Please enter quantity")
	private Long quantity;
	@Basic
	private Long quantityRemaining;
	@Column(columnDefinition = "varchar(255) default 'withdraw'")
	private String transactionType;
	@Lob
	private String comment;
	@Column(columnDefinition = "boolean default true")
	private Boolean transactionStatus = true;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime creationDateTime;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	@Transient																		// ignore field creation in database
	private Long cnt;
	@Transient																		// ignore field creation in database
	private Integer monthNum;
	
	/**
	 * Default constructor
	 */
	public Transaction() { }
	
	/**
	 * Argument constructor
	 */
	public Transaction(Long quantity, Long quantityRemaining, String transactionType, String comment, User user, Product product, Long cnt) {
		super();
		this.quantity = quantity;
		this.quantityRemaining = quantityRemaining;
		this.transactionType = transactionType;
		this.comment = comment;
		this.user = user;
		this.product = product;
		this.cnt = cnt;
	}
	
	/**
	 * Two Argument constructor
	 */
	public Transaction(Integer monthNum, Long cnt) {
		super();
		this.monthNum = monthNum;
		this.cnt = cnt;
	}
	
	/*
	 * getters and setters according to entities
	 */
	public Long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Boolean getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(Boolean transactionStatus) {
		this.transactionStatus = transactionStatus;
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
	public Long getQuantityRemaining() {
		return quantityRemaining;
	}
	public void setQuantityRemaining(Long quantityRemaining) {
		this.quantityRemaining = quantityRemaining;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getCnt() {
		return cnt;
	}
	public void setCnt(Long countProducts) {
		this.cnt = countProducts;
	}
	public Integer getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}
	
}
