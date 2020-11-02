package org.balramjot.quiktraker.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * Model to hold contact us data
 * Used with JPA to add entity and model to the database
 * Jpa validations used are :
 * @NotNull - field must be filled
 * Holding many to one relationship with the user class
 * @author BjSaini
 * @version 1.0
 */

@Entity
@Table(name="contactus")
public class ContactUs {
	
	/*
	 * Entities
	 * @contactId - contact id for the contact messages
	 * @subject - subject for the contact messages
	 * @message - message for the contact messages
	 * @creationDateTime - insertion date time for the contact us message which is current time by default
	 * @user - many to one relationship with the users
	 * @markRead - list to hold values during check all
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long contactId;
	@Basic
	@NotNull(message="Please enter subject")
	private String subject;
	@Lob
	private String message;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime creationDateTime;
	@Column(columnDefinition = "boolean default false")
	private Boolean contactStatus = false;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	@Transient																	// ignore field creation in database
	private List<Long> markRead;										

	/**
	 * Default constructor
	 */
	public ContactUs() {}
	
	/**
	 * Argument constructor
	 */
	public ContactUs(@NotNull(message = "Please enter subject") String subject, String message, User user) {
		super();
		this.subject = subject;
		this.message = message;
		this.user = user;
	}
	
	/*
	 * getters and setters according to entities
	 */
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public Boolean getContactStatus() {
		return contactStatus;
	}
	public void setContactStatus(Boolean contactStatus) {
		this.contactStatus = contactStatus;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Long> getMarkRead() {
		return markRead;
	}
	public void setMarkRead(List<Long> markRead) {
		this.markRead = markRead;
	}
	
}
