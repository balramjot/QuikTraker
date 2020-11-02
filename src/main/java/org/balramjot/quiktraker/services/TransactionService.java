package org.balramjot.quiktraker.services;

import java.util.List;

import org.balramjot.quiktraker.dao.TransactionRepoIF;
import org.balramjot.quiktraker.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service for transaction controller
 * @author BjSaini
 * @version 1.0
 */
@Service
public class TransactionService {

	/**
	 * connecting the transaction repository
	 */
	@Autowired
	TransactionRepoIF transactionRepoIf;
	
	/**
	 * method to save transaction
	 * @param u which is transaction
	 */
	public void save(Transaction u) {
		transactionRepoIf.save(u);
	}
	
	/**
	 * method to get number of transactions done by the particular user
	 * @param userId which is the id of the current logged in user
	 * @return count of the transactions in long
	 */
	public Long countTransactionbyUserId(Long userId) {
		return transactionRepoIf.countByUserUserId(userId);
	}
	
	/**
	 * method to get list of transactions done by the particular user
	 * sorted according to creation date time in descending order
	 * @param userId which is the id of the current logged in user
	 * @return list of the transactions
	 */
	public List<Transaction> showAllTransactionsUserAccTime(Long userId) {
		return transactionRepoIf.findByUserUserIdOrderByCreationDateTimeDesc(userId);
	}
	
	/**
	 * method to get list of transactions performed according to products in descending order
	 * @param limitPage - limit records to fetch in return
	 * @return list of transactions
	 */
	Pageable limitPage = PageRequest.of(0,7);
	public List<Transaction> showMostTransactionsProduct() {
		return transactionRepoIf.findByMostTransactionProduct(limitPage);
	}
	
	/**
	 * method to get list of transactions performed according to users in descending order
	 * @param limitPage - limit records to fetch in return
	 * @return list of transactions
	 */
	public List<Transaction> showMostTransactionsUser() {
		return transactionRepoIf.findByMostTransactionUser(limitPage);
	}
	
	/**
	 * method to get list of transactions according to months
	 * @return list of transactions
	 */
	public List<Transaction> showTransactionPerMonth() {
		return transactionRepoIf.findByTransactionPerMonth();
	}
	
	/**
	 * method to get list of transactions according to months and particular user
	 * @param userId which is the id of the current logged in user
	 * @return list of transactions
	 */
	public List<Transaction> showTransactionPerMonthByUser(Long userId) {
		return transactionRepoIf.findByTransactionPerMonthUser(userId);
	}
	
}
