package org.balramjot.quiktraker.models;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import org.balramjot.quiktraker.annotations.ValidFirstNameEditProfile;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Model to hold user information data
 * Used with JPA to add entity and model to the database
 * Holding one to one relationship with the user class
 * @author BjSaini
 * @version 1.0
 */
@ValidFirstNameEditProfile(message="Please enter first name")
@Entity
@Table(name = "userinfo")
public class UserInfo {
	
	/*
	 * Entities
	 * @userInfoId - user information id for the user inforamtion
	 * @address - address of the user
	 * @zipcode - zipcode of the user
	 * @province - province of the user
	 * @phoneNo - phone number of the user
	 * @profilePic - profile picture of the user
	 * @creationDateTime - insertion date time for the user information which is current time by default
	 * @userStatus - user information status which is true by default
	 * @user - one to one relationship with the user
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userInfoId;
	@Basic
	private String address;
	private String zipcode;
	private String city;
	private String province;
	private Long phoneNo;
	@Lob
	private String profilePic;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime creationDateTime;
	@Column(columnDefinition = "boolean default true")
	private Boolean userStatus = true;
	@OneToOne(mappedBy = "userInfo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })			// mapped by = Model class
	private User user;
	
	/**
	 * Default constructor
	 */
	public UserInfo() {}
	
	/**
	 * Argument constructor
	 */
	public UserInfo(String address, String zipcode, String city, String province, Long phoneNo, String profilePic) {
		super();
		this.address = address;
		this.zipcode = zipcode;
		this.city = city;
		this.province = province;
		this.phoneNo = phoneNo;
		this.profilePic = profilePic;
		
	}
	
	/*
	 * getters and setters according to entities
	 */
	public Long getUserInfoId() {
		return userInfoId;
	}
	public void setUserInfoId(Long userInfoId) {
		this.userInfoId = userInfoId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Long getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public Boolean getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
