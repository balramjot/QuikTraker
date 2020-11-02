package org.balramjot.quiktraker.dao;

import java.util.List;

import org.balramjot.quiktraker.models.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for contact us service class
 * @author BjSaini
 *
 */
@Repository
public interface UserContactUsRepoIF extends JpaRepository<ContactUs, Long> {
	
	/**
	 * method to get list of all the contact messages
	 * sorted according to creation date time in descending order
	 * @return list of contact us messages
	 */
	List<ContactUs> findAllByOrderByCreationDateTimeDesc();
	
	/**
	 * method to get count of the messages according to contact status
	 * @param b status of the contact messages i.e. true for read and false for unread
	 * @return count of the messages according to the parameter
	 */
	Long countByContactStatus(boolean b);
	
	/**
	 * method to get list of the messages according to contact status
	 * @param b status of the contact messages i.e. true for read and false for unread
	 * @return list of the messages according to the parameter
	 */
	List<ContactUs> findByContactStatus(boolean b);
	
	/**
	 * method to get list of all the contact messages
	 * sorted according to contact status in ascending order
	 * i.e. unread messages first followed by read messages
	 * @return list of contact us messages
	 */
	List<ContactUs> findAllByOrderByContactStatusAsc();
}
