package org.balramjot.quiktraker.models;

import javax.validation.constraints.NotNull;

import org.balramjot.quiktraker.annotations.ValidPassword;

/**
 * Model to change password page data
 * jpa validations are used
 * Jpa validations used are :
 * @NotNull - field must be filled
 * @ValidPassword - check if password as per requirements set in regex expression
 * @author BjSaini
 * @version 1.0
 */
public class UserChangePassword {
	
	/*
	 * Entities
	 * @password - current password
	 * @newPassword - new password
	 */
	
	@NotNull(message="Please enter password")
	@ValidPassword(message = "Invalid Current Password. Please check current password specifications")
	private String password;
	@NotNull(message="Please enter password")
	@ValidPassword(message = "Invalid New Password. Please check new password specifications")
	private String newPassword;
	
	/**
	 * Default constructor
	 */
	public UserChangePassword() {}
	
	/**
	 * Argument constructor
	 */
	public UserChangePassword(@NotNull(message = "Please enter password") String password,
			@NotNull(message = "Please enter password") String newPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
	}
	

	/*
	 * getters and setters according to entities
	 */
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
