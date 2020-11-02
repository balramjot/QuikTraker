package org.balramjot.quiktraker.dao;

import java.util.List;

import org.balramjot.quiktraker.models.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for transaction service class
 * @author BjSaini
 *
 */
@Repository
public interface TransactionRepoIF extends JpaRepository<Transaction, Long> {
	
	/**
	 * method to get number of transactions done by the particular user
	 * @param userId which is the id of the current logged in user
	 * @return count of the transactions in long
	 */
	Long countByUserUserId(Long userId);
	
	/**
	 * method to get list of transactions done by the particular user
	 * sorted according to creation date time in descending order
	 * @param userId which is the id of the current logged in user
	 * @return list of the transactions
	 */
	List<Transaction> findByUserUserIdOrderByCreationDateTimeDesc(Long userId);
	
	/**
	 * method to get list of transactions performed according to products in descending order
	 * @param pageable - limit records to fetch in return
	 * @return list of transactions
	 */
	@Query("select new org.balramjot.quiktraker.models.Transaction(t.quantity,t.quantityRemaining,t.transactionType,t.comment,t.user,t.product,COUNT(t.product.productId)) from Transaction AS t GROUP BY t.product.productId ORDER BY COUNT(t.product.productId) DESC")
	List<Transaction> findByMostTransactionProduct(Pageable pageable);
	
	/**
	 * method to get list of transactions performed according to users in descending order
	 * @param pageable - limit records to fetch in return
	 * @return list of transactions
	 */
	@Query("select new org.balramjot.quiktraker.models.Transaction(t.quantity,t.quantityRemaining,t.transactionType,t.comment,t.user,t.product,COUNT(t.user.userId)) from Transaction AS t WHERE t.user.role NOT IN ('ROLE_ADMIN') GROUP BY t.user.userId ORDER BY COUNT(t.user.userId) DESC")
	List<Transaction> findByMostTransactionUser(Pageable pageable);
	
	/**
	 * method to get list of transactions according to months
	 * @return list of transactions
	 */
	@Query("select new org.balramjot.quiktraker.models.Transaction(MONTH(t.creationDateTime),COUNT(t.transactionId)) from Transaction AS t GROUP BY MONTH(t.creationDateTime),YEAR(t.creationDateTime)")
	List<Transaction> findByTransactionPerMonth();
	
	/**
	 * method to get list of transactions according to months and particular user
	 * @param userId which is the id of the current logged in user
	 * @return list of transactions
	 */
	@Query("select new org.balramjot.quiktraker.models.Transaction(MONTH(t.creationDateTime),COUNT(t.transactionId)) from Transaction AS t WHERE t.user.userId = ?1 GROUP BY MONTH(t.creationDateTime),YEAR(t.creationDateTime)")
	List<Transaction> findByTransactionPerMonthUser(Long userId);
	
}
