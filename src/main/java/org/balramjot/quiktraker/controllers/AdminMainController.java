package org.balramjot.quiktraker.controllers;


import java.text.DateFormatSymbols;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.balramjot.quiktraker.models.Product;
import org.balramjot.quiktraker.models.Transaction;
import org.balramjot.quiktraker.services.AdminProductService;
import org.balramjot.quiktraker.services.TransactionService;
import org.balramjot.quiktraker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Controller that controls main landing page of the admin
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class AdminMainController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	AdminProductService adminProductService;
	@Autowired
	TransactionService transactionService;
	
	/**
	 * Initial method
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * @GET
	 * @working filled administrator dashboard page with dynamic data
	 * methods uses different services to get the dynamic database results and
	 * show them in graphs and tables
	 * @param formData to add form attributes
	 * @param authentication for user authentication
	 * @return html page
	 */
	@GetMapping("/adminDashboard")
	public String adminDashboardPage(Model formData, Authentication authentication) {
		Long usersCnt = userService.getTotalUsers();
		Long productsCnt = adminProductService.countAllProducts();
		Long productsOutOfStockCnt = adminProductService.countAllOutOfStockProducts();
		Long productsCriticalCnt = adminProductService.countAllLowQuantity();
		List<Transaction> mostTransactionByProduct = transactionService.showMostTransactionsProduct();
		List<Transaction> mostTransactionByUser = transactionService.showMostTransactionsUser();
		List<Transaction> transactionPerMonth = transactionService.showTransactionPerMonth();
		Map<String, Long> barChartMap = new LinkedHashMap<>();
		
		transactionPerMonth.forEach(t-> {
			String monthString = new DateFormatSymbols().getMonths()[t.getMonthNum()-1];
			barChartMap.put(monthString, t.getCnt());
		});
		
		List<Product> productsAccCategory = adminProductService.showAllProductCategoryProducts();		
		
		formData.addAttribute("usersCnt", usersCnt);
		formData.addAttribute("productsCnt", productsCnt);
		formData.addAttribute("productsCriticalCnt", productsCriticalCnt);
		formData.addAttribute("productsOutOfStockCnt", productsOutOfStockCnt);
		formData.addAttribute("mostTransactionByProduct", mostTransactionByProduct);
		formData.addAttribute("mostTransactionByUser", mostTransactionByUser);
		formData.addAttribute("barChartMap", barChartMap);
		
		formData.addAttribute("pieChartMapLabel1", productsAccCategory.get(0).getProductCategory().getCategoryName());
		formData.addAttribute("pieChartMapLabel2", productsAccCategory.get(1).getProductCategory().getCategoryName());
		formData.addAttribute("pieChartMapLabel3", productsAccCategory.get(2).getProductCategory().getCategoryName());
		formData.addAttribute("pieChartMapLabel4", productsAccCategory.get(3).getProductCategory().getCategoryName());
		formData.addAttribute("pieChartMapLabel5", productsAccCategory.get(4).getProductCategory().getCategoryName());
		formData.addAttribute("pieChartMapLabel6", productsAccCategory.get(5).getProductCategory().getCategoryName());
		formData.addAttribute("pieChartMapLabel7", productsAccCategory.get(6).getProductCategory().getCategoryName());
		
		formData.addAttribute("pieChartMapValue1", productsAccCategory.get(0).getCnt());
		formData.addAttribute("pieChartMapValue2", productsAccCategory.get(1).getCnt());
		formData.addAttribute("pieChartMapValue3", productsAccCategory.get(2).getCnt());
		formData.addAttribute("pieChartMapValue4", productsAccCategory.get(3).getCnt());
		formData.addAttribute("pieChartMapValue5", productsAccCategory.get(4).getCnt());
		formData.addAttribute("pieChartMapValue6", productsAccCategory.get(5).getCnt());
		formData.addAttribute("pieChartMapValue7", productsAccCategory.get(6).getCnt());
		return "admin/admin_dashboard";
	}
	
	
}
