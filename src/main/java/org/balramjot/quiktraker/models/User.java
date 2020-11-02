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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.balramjot.quiktraker.annotations.ValidEmail;
import org.balramjot.quiktraker.annotations.ValidPassword;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Model to hold user data
 * Used with JPA to add entity and model to the database
 * Jpa validations used are :
 * @NotNull - field must be filled
 * @ValidEmail - checking correct syntax for the email
 * @ValidPassword - check if password as per requirements set in regex expression
 * Holding one to one relationship with the user contact us class
 * Holding one to many relationship with the transaction class
 * Holding one to many relationship with the favorites class
 * @author BjSaini
 * @version 1.0
 */
@Entity
@Table(name="users")
public class User {
	
	/*
	 * Entities
	 * @userId - user id for the user
	 * @firstName - first name of the user
	 * @lastName - last name of the user
	 * @email - email of the user which is unique
	 * @password - password for the user stored in encrypted form
	 * @enabled - to make user enable to access his or her account which is true by default
	 * @creationDateTime - insertion date time for the product which is current time by default
	 * @userStatus - user status which is true by default
	 * @role - to hold user role which is set for user by default
	 * @userInfo - one to one relationship with user information class
	 * @contactUsList - one to many relationship with the contact us
	 * @transactionList - one to many relationship with the transactions
	 * @favouritesList - one to many relationship with the favorites products
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	@Basic
	@NotNull(message="Please enter first name")
	private String firstName;
	private String lastName;
	@Column(unique=true)
	@NotNull(message="Please enter an email address")
	@ValidEmail(message="Please enter valid email address")
	private String email;
	@NotNull(message="Please enter password")
	@ValidPassword(message = "Invalid Password. Please check password specifications")
	private String password;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime creationDateTime;
	@Column(columnDefinition = "boolean default true")
	private Boolean userStatus = true;
	@Column(columnDefinition = "boolean default true")
	private Boolean enabled = true;
	@Column(columnDefinition = "varchar(255) default 'ROLE_USER'")
	private String role = "ROLE_USER";
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userInfoId")					// name = model entity
	private UserInfo userInfo;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<ContactUs> contactUsList;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Transaction> transactionList;
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	private List<Favourites> favouritesList;
	

	/**
	 * Default constructor
	 */
	public User() {}
	
	/**
	 * Argument constructor
	 */
	public User(String firstName, String lastName, String email, String password, List<ContactUs> contactUsList, List<Transaction> transactionList, List<Favourites> favouritesList) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.contactUsList = contactUsList;
		this.transactionList = transactionList;
		this.favouritesList = favouritesList;
	}
	
	/**
	 * Two Argument constructor
	 */
	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	

	/*
	 * getters and setters according to entities
	 */
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<ContactUs> getContactUsList() {
		return contactUsList;
	}
	public void setContactUsList(List<ContactUs> contactUsList) {
		this.contactUsList = contactUsList;
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

}
