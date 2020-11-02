package org.balramjot.quiktraker.services;

import java.util.List;

import org.balramjot.quiktraker.dao.UserContactUsRepoIF;
import org.balramjot.quiktraker.models.ContactUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for contact us controller
 * @author BjSaini
 * @version 1.0
 */
@Service
public class UserContactUsSerive {
	
	/**
	 * connecting the contact us repository
	 */
	@Autowired
	UserContactUsRepoIF userContactUsRepoIf;
	
	/**
	 * method to save contact us message
	 * @param u which is contact us message
	 */
	public void save(ContactUs u) {
		userContactUsRepoIf.save(u);
	}
	
	/**
	 * method to get list of all the contact messages
	 * sorted according to creation date time in descending order
	 * @return list of contact us messages
	 */
	public List<ContactUs> showAllMessages() {
		return userContactUsRepoIf.findAllByOrderByCreationDateTimeDesc();
	}
	
	/**
	 * method to check if contact us message exist in the database or not
	 * @param id - contact id
	 * @return boolean - true if contact us message exists and false if not
	 */
	public boolean existById(long id) {
		return userContactUsRepoIf.existsById(id);
	}
	
	/**
	 * method to get list of contact us message if exist in the database
	 * @param id - contact id
	 * @return contact us message if exist otherwise null
	 */
	public ContactUs findMessagebyId(long id) {
		if(existById(id))
			return userContactUsRepoIf.findById(id).get();
		else
			return null;
	}
	
	/**
	 * method to delete contact us message if exist in the database
	 * @param id - contact id
	 */
	public void deleteMessageById(long id) {
		if(existById(id))
			userContactUsRepoIf.deleteById(id);
	}
	
	/**
	 * method to get count of the messages according to contact status
	 * @param b status of the contact messages i.e. true for read and false for unread
	 * @return count of the messages according to the parameter
	 */
	public Long countUnreadMessages(boolean b) {
		return userContactUsRepoIf.countByContactStatus(b);
	}
	
	/**
	 * method to get list of the messages according to contact status
	 * @param b status of the contact messages i.e. true for read and false for unread
	 * @return list of the messages according to the parameter
	 */
	public List<ContactUs> showUnreadAllMessages(boolean b) {
		return userContactUsRepoIf.findByContactStatus(b);
	}
	
	/**
	 * method to get list of all the contact messages
	 * sorted according to contact status in ascending order
	 * i.e. unread messages first followed by read messages
	 * @return list of contact us messages
	 */
	public List<ContactUs> showUnreadMessages() {
		return userContactUsRepoIf.findAllByOrderByContactStatusAsc();
	}
	
}
