package org.balramjot.quiktraker;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.balramjot.quiktraker.controllers.FrontMainController;
import org.balramjot.quiktraker.dao.AdminCategoryRepoIF;
import org.balramjot.quiktraker.dao.AdminProductRepoIF;
import org.balramjot.quiktraker.dao.FavouritesRepoIF;
import org.balramjot.quiktraker.dao.TransactionRepoIF;
import org.balramjot.quiktraker.dao.UserContactUsRepoIF;
import org.balramjot.quiktraker.dao.UserRepoIF;
import org.balramjot.quiktraker.models.ContactUs;
import org.balramjot.quiktraker.models.Favourites;
import org.balramjot.quiktraker.models.Product;
import org.balramjot.quiktraker.models.ProductCategory;
import org.balramjot.quiktraker.models.Transaction;
import org.balramjot.quiktraker.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SainiBalramjotQuiktrakerCaseStudyApplicationTests {
	
	YearMonth thisMonth = YearMonth.now();	
	
	@Autowired
	FrontMainController frontMainController;
	@Autowired
	UserRepoIF userRepoIf;
	@Autowired
	UserContactUsRepoIF userContactUsRepoIF;
	@Autowired
	AdminCategoryRepoIF adminCategoryRepoIf;
	@Autowired
	FavouritesRepoIF favoritesRepoIf;
	@Autowired
	TransactionRepoIF transactionRepoIf;
	@Autowired
	AdminProductRepoIF adminProductRepoIf;
	
	
	@Test
	void contextLoads() {
		assertThat(frontMainController).isNotNull();
	}
	
	/******* for sign up and sign in ******/
	
	@ParameterizedTest
	@CsvSource({"john@doe.com,true","wills@quicktracker.com,true","balram@outlook.com,false"})
	public void testfindByEmail(String email, boolean result) {
		Optional<User> expected = userRepoIf.findByEmail(email);
		assertThat(expected.isPresent()).isEqualTo(result);
	}
	
	@Test
	public void testCountByRole() {
		Long expected = userRepoIf.countByRole("ROLE_USER");
		assertThat(expected).isGreaterThan(1);
	}
	
	@Test
	public void testFindUserListByRoleDateTimeDesc() {
		List<User> expected = userRepoIf.findByRoleOrderByCreationDateTimeDesc("ROLE_USER");
		assertThat(expected.get(0).getCreationDateTime()).isAfterOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
	}
	
	/******* for contact us ******/
	
	@Test
	public void testFindContactListByDateTimeDesc() {
		List<ContactUs> expected = userContactUsRepoIF.findAllByOrderByCreationDateTimeDesc();
		assertThat(expected.get(0).getCreationDateTime()).isAfterOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
	}
	
	@Test
	public void testCheckUnreadMessages() {
		Long expected = userContactUsRepoIF.countByContactStatus(false);
		assertThat(expected).isGreaterThanOrEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({"true,true","false,false"})
	public void testFindContactListAccToStatus(boolean input, boolean result) {
		List<ContactUs> expected = userContactUsRepoIF.findByContactStatus(input);
		assertThat(expected.get(0).getContactStatus()).isEqualTo(result);
	}
	
	@Test
	public void testFindContactListByUnreadFirst() {
		List<ContactUs> expected = userContactUsRepoIF.findAllByOrderByContactStatusAsc();
		assertThat(expected.get(0).getContactStatus()).isEqualTo(false);
	}
	
	/******* for product category ******/
	
	@Test
	public void testFindCategoryByDateTime() {
		List<ProductCategory> expected = adminCategoryRepoIf.findAllByOrderByCreationDateTimeDesc();
		assertThat(expected.get(0).getCreationDateTime()).isAfterOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
	}
	
	@Test
	public void testFindCategoryByStatusByDateTime() {
		List<ProductCategory> expected = adminCategoryRepoIf.findByCategoryStatusTrueOrderByCreationDateTimeDesc();
		assertThat(expected.get(0).getCategoryStatus()).isEqualTo(true);
		assertThat(expected.get(0).getCreationDateTime()).isAfterOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
	}
	
	/******* for my favorites ******/
	
	@ParameterizedTest
	@CsvSource({"15,7,true", "17,6,true", "5,6,false"})
	public void testIfProductExistForUser(long productId, long userId, boolean result) {
		boolean expected = favoritesRepoIf.existsByProductProductIdAndUserUserId(productId, userId);
		assertThat(expected).isEqualTo(result);
	}
	
	@ParameterizedTest
	@CsvSource({"6,8", "7,6"})
	public void testCountFavorites(long userId, int result) {
		Long expected = favoritesRepoIf.countByUserUserId(userId);
		assertThat(expected).isEqualTo(result);
	}
	
	@ParameterizedTest
	@CsvSource({"7,14", "6,18"})
	public void testFavListAccDateTimeDsc(long userId, long result) {
		List<Favourites> expected = favoritesRepoIf.findByUserUserIdOrderByCreationDateTimeDesc(userId);
		assertThat(expected.get(0).getCreationDateTime()).isAfterOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
		assertThat(expected.get(0).getFavouriteId()).isEqualTo(result);
		
	}
	
	/******* for transactions ******/
	
	@ParameterizedTest
	@CsvSource({"6,12", "7,10"})
	public void testCountUserTransactions(long userId, int result) {
		Long expected = transactionRepoIf.countByUserUserId(userId);
		assertThat(expected).isEqualTo(result);
	}
	
	@ParameterizedTest
	@CsvSource({"6,154", "7,140"})
	public void testGetUserTransAccToDateTimeDesc(long userId, long result) {
		List<Transaction> expected = transactionRepoIf.findByUserUserIdOrderByCreationDateTimeDesc(userId);
		assertThat(expected.get(0).getCreationDateTime()).isAfterOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
		assertThat(expected.get(0).getTransactionId()).isEqualTo(result);
	}

	@ParameterizedTest
	@CsvSource({"6,3", "7,1"})
	public void testFindTransPerMonthByUser(long userId, long result) {
		List<Transaction> expected = transactionRepoIf.findByTransactionPerMonthUser(userId);
		assertThat(expected.get(0).getCnt()).isEqualTo(result);
	}

	/******* for products ******/
	
	@Test
	public void testFindProductByDateTimeDesc() {
		List<Product> expected = adminProductRepoIf.findAllByOrderByCreationDateTimeDesc();
		assertThat(expected.get(0).getCreationDateTime()).isAfterOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
	}
	
	@Test
	public void testFindProductByStatusByDateTimeDesc() {
		List<Product> expected = adminProductRepoIf.findByProductStatusTrueOrderByCreationDateTimeDesc();
		assertThat(expected.get(0).getProductStatus()).isEqualTo(true);
		assertThat(expected.get(0).getCreationDateTime()).isAfterOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
	}
	
	@Test
	public void testFindProductByStatusByDateTimeAsc() {
		List<Product> expected = adminProductRepoIf.findByProductStatusTrueOrderByCreationDateTimeAsc();
		assertThat(expected.get(0).getProductStatus()).isEqualTo(true);
		assertThat(expected.get(0).getCreationDateTime()).isBeforeOrEqualTo(expected.get(expected.size()-1).getCreationDateTime());
	}
	
	@Test
	public void testFindProductByStatusByInStockDesc() {
		List<Product> expected = adminProductRepoIf.findByProductStatusTrueOrderByInStockDesc();
		assertThat(expected.get(0).getProductStatus()).isEqualTo(true);
		assertThat(expected.get(0).getInStock()).isGreaterThanOrEqualTo(expected.get(expected.size()-1).getInStock());
	}
	
	@Test
	public void testFindProductByStatusByInStockAsc() {
		List<Product> expected = adminProductRepoIf.findByProductStatusTrueOrderByInStockAsc();
		assertThat(expected.get(0).getProductStatus()).isEqualTo(true);
		assertThat(expected.get(0).getInStock()).isLessThanOrEqualTo(expected.get(expected.size()-1).getInStock());
	}
	
	@Test
	public void testFindProductByInStockAsc() {
		List<Product> expected = adminProductRepoIf.findAllByOrderByInStockAsc();
		assertThat(expected.get(0).getInStock()).isLessThanOrEqualTo(expected.get(expected.size()-1).getInStock());
	}
	
	@ParameterizedTest
	@CsvSource({"1"})
	public void testCountOutofStockByProducts(int result) {
		Long expected = adminProductRepoIf.countByInStock(0);
		assertThat(expected).isEqualTo(result);
	}
	
	@Test
	public void testCountLowQuantityProducts() {
		Long expected = adminProductRepoIf.countByLowQuantity();
		assertThat(expected).isGreaterThanOrEqualTo(1);
	}
	
	@ParameterizedTest
	@CsvSource({"Paper,9", "Basic tool kit,5"})
	public void testSearchInventory(String keyword, int result) {
		List<Product> expected = adminProductRepoIf.searchInventory(keyword);
		assertThat(expected.get(0).getProductId()).isEqualTo(result);
	}
	
}
