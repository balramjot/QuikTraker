package org.balramjot.quiktraker.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.balramjot.quiktraker.models.Favourites;
import org.balramjot.quiktraker.models.Product;
import org.balramjot.quiktraker.models.Transaction;
import org.balramjot.quiktraker.models.User;
import org.balramjot.quiktraker.services.AdminProductService;
import org.balramjot.quiktraker.services.FavouriteService;
import org.balramjot.quiktraker.services.TransactionService;
import org.balramjot.quiktraker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @RestController
 * @author BjSaini
 * @version 1.0
 *
 */
@RestController
public class RestWebsiteController {
	
	/**
	 * connection to the respective services
	 */
	@Autowired
	UserService userService;
	@Autowired
	AdminProductService adminProductService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	FavouriteService favouriteService;
	
	/**
	 * @POST
	 * @working upload user profile picture to the folder and database using ajax
	 * method checks if file to be uploaded is empty or not, if empty then return error message
	 * otherwise, upload the file 
	 * @param file which needs to be uploaded
	 * @return string to be used in ajax jquery output
	 */
	@PostMapping("/api/file/upload")
    public String uploadMultipartFile(@RequestParam("picture") MultipartFile file) {
	      try {
	    	  
	    	  String uploadDir = "./src/main/resources/static/uploaded_images";
	  		
	  		if (!file.getOriginalFilename().isEmpty()) {
	  			BufferedOutputStream outputStream = new BufferedOutputStream(
	  					new FileOutputStream(new File(uploadDir, file.getOriginalFilename())));
	  			outputStream.write(file.getBytes());
	  			outputStream.flush();
	  			outputStream.close();
	  		} else {
	  			return "Please select at least one picture to upload@=@1";
	  		}
	  		
	        return "Profile picture uploaded successfully. Please click on UPDATE button to save your changes permanently@=@2@=@" + file.getOriginalFilename();
	    } catch (  Exception e) {
	      return "Something went wrong. Please try again !!!";
	    }    
    }
	
	/**
	 * @POST
	 * @working to update product quantity via administrator end
	 * method checks if administrator is authenticated or not
	 * if authentication successful then, check if product exist or not, if product exist then,
	 * checks type of transaction, if it is withdraw then,
	 * check if there is enough quantity in the database to be withdraw if available then, update quantity and save changes,
	 * if transaction type is deposit, then make sure that quantity in stock after depositing
	 * transaction quantity should be less than or equal to alloted quantity for that product
	 * else, return error message string and stop code execution
	 * @param qty to be updated
	 * @param id of the product
	 * @param txnType wither deposit or withdraw
	 * @param comment to be included while performing any transaction
	 * @param authentication for user authentication
	 * @return string to be used in ajax jquery output
	 */
	@PostMapping("/admin/rest/updateQuantity")
    public String adminUpdateQuantityProduct(@RequestParam("updateInStock") Long qty, @RequestParam("hiddenProductId") long id, @RequestParam("trns_option") String txnType, @RequestParam("comment") String comment, Authentication authentication) {
		try {
			
			if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
				User user = userService.checkEmailAvailability(authentication.getName());
				if(user != null) {
					Product product = adminProductService.findProductById(id);
					if(product != null) {
						if(txnType.equals("withdraw")) {
							
							if(product.getInStock() >= qty) {
								product.setInStock(product.getInStock() - qty);
								adminProductService.save(product);
								
								Transaction transaction = new Transaction();
								transaction.setProduct(product);
								transaction.setUser(user);
								transaction.setQuantity(qty);
								transaction.setTransactionType(txnType);
								transaction.setQuantityRemaining(product.getInStock());
								transaction.setComment(comment);
								transactionService.save(transaction);
								return "Quantity transaction has been completed successfully@=@"+product.getInStock()+"@=@"+id;
							} else {
								return "Oops !! Not that much quantity in stock@=@notExist";
							}							
						} else {
							
							if(product.getAllotedQuantity() >= product.getInStock() + qty) {
								product.setInStock(product.getInStock() + qty);
								adminProductService.save(product);
								Transaction transaction = new Transaction();
								transaction.setProduct(product);
								transaction.setUser(user);
								transaction.setQuantity(qty);
								transaction.setTransactionType(txnType);
								transaction.setQuantityRemaining(product.getInStock());
								transaction.setComment(comment);
								transactionService.save(transaction);
								return "Quantity transaction has been completed successfully@=@"+product.getInStock()+"@=@"+id;
							} else {
								return "Quantity in stock should be less than or equal to: " + product.getAllotedQuantity() + "@=@notExist";
							}
						}
						
					} else {
						return "Product not exist@=@notExist";
					}
				} else {
					return "Something went wrong. Please try again !!!@=@notExist";
				}
			} else {
				return "Something went wrong. Please try again !!!@=@notExist";
			}
			
		} catch (  Exception e) {
	      return "Something went wrong. Please try again !!!@=@notExist";
	    }  
    }
	
	/**
	 * @POST
	 * @working to update product quantity via user end
	 * method checks if user is authenticated or not
	 * if authentication successful then, check if product exist or not, if product exist then,
	 * checks type of transaction, if it is withdraw then,
	 * check if there is enough quantity in the database to be withdraw if available then, update quantity and save changes,
	 * if transaction type is deposit, then make sure that quantity in stock after depositing
	 * transaction quantity should be less than or equal to alloted quantity for that product
	 * else, return error message string and stop code execution
	 * @param qty to be updated
	 * @param id of the product
	 * @param txnType wither deposit or withdraw
	 * @param comment to be included while performing any transaction
	 * @param authentication for user authentication
	 * @return string to be used in ajax jquery output
	 */
	@PostMapping("/user/rest/updateTransctionQuantity")
    public String userUpdateTransactionQuantity(@RequestParam("transactionQuantity") Long qty, @RequestParam("hiddenProductId") long id, @RequestParam("trns_option") String txnType, @RequestParam("comment") String comment, Authentication authentication) {
		try {
			
			if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
				User user = userService.checkEmailAvailability(authentication.getName());
				if(user != null) {
					Product product = adminProductService.findProductById(id);
					if(product != null) {
						if(txnType.equals("withdraw")) {
							
							if(product.getInStock() >= qty) {
								product.setInStock(product.getInStock() - qty);
								adminProductService.save(product);
								
								Transaction transaction = new Transaction();
								transaction.setProduct(product);
								transaction.setUser(user);
								transaction.setQuantity(qty);
								transaction.setTransactionType(txnType);
								transaction.setQuantityRemaining(product.getInStock());
								transaction.setComment(comment);
								transactionService.save(transaction);
								return "Quantity transaction has been completed successfully@=@"+product.getInStock()+"@=@"+id;
							} else {
								return "Oops !! Not that much quantity in stock@=@notExist";
							}							
						} else {
							
							if(product.getAllotedQuantity() >= product.getInStock() + qty) {
								product.setInStock(product.getInStock() + qty);
								adminProductService.save(product);
								Transaction transaction = new Transaction();
								transaction.setProduct(product);
								transaction.setUser(user);
								transaction.setQuantity(qty);
								transaction.setTransactionType(txnType);
								transaction.setQuantityRemaining(product.getInStock());
								transaction.setComment(comment);
								transactionService.save(transaction);
								return "Quantity transaction has been completed successfully@=@"+product.getInStock()+"@=@"+id;
							} else {
								return "Quantity in stock should be less than or equal to: " + product.getAllotedQuantity() + "@=@notExist";
							}
						}
						
					} else {
						return "Product not exist@=@notExist";
					}
				} else {
					return "Something went wrong. Please try again !!!@=@notExist";
				}
			} else {
				return "Something went wrong. Please try again !!!@=@notExist";
			}
			
		} catch (  Exception e) {
	      return "Something went wrong. Please try again !!!@=@notExist";
	    }
    }
	
	/**
	 * @POST
	 * @working method to add or remove products from favorite list
	 * method checks if user is authenticated then, check if product exist
	 * after that checks if that product is already in the logged in user favorites list
	 * if it already exists then, remove the record from favorite list otherwise add it
	 * in case of error, return error message string and stop code execution
	 * @param id of the product
	 * @param authentication for user authentication
	 * @return string to be used in ajax jquery output
	 */
	@PostMapping("/user/rest/addToFavourite")
    public String addToFavouriteProduct(@RequestParam("favId") long id, Authentication authentication) {
		try {
			if(userService.checkUserExistorNotByEmail(authentication.getName()) == true) {
				User user = userService.checkEmailAvailability(authentication.getName());
				if(user != null) {
					Product product = adminProductService.findProductById(id);
					if(product != null) {
						Favourites favourite = favouriteService.findFavouritebyProductIdUserId(id, user.getUserId());
						if(favourite != null) {
							favouriteService.deleteFavouriteById(favourite.getFavouriteId());
							Long countFav = favouriteService.countFavouritebyUserId(user.getUserId());
							return "delete@=@"+id+"@=@"+countFav;							
						} else {
							Favourites newFavourite = new Favourites();
							newFavourite.setProduct(product);
							newFavourite.setUser(user);
							favouriteService.save(newFavourite);
							Long countFav = favouriteService.countFavouritebyUserId(user.getUserId());
							return "insert@=@"+id+"@=@"+countFav;
						}
					} else {
						return "Product not exist@=@notExist";
					}
				} else {
					return "Something went wrong. Please try again !!!@=@notExist";
				}
			} else {
				return "Something went wrong. Please try again !!!@=@notExist";
			}	
		} catch (  Exception e) {
	      return "Something went wrong. Please try again !!!@=@notExist";
	    }
    }
	
	
	
}
