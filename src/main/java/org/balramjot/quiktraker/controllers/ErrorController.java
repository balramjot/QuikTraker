package org.balramjot.quiktraker.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller handles all the errors such as
 * 404, 500, 405.
 * By implementing spring error controller interface
 * @author BjSaini
 * @version 1.0
 */
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
	
	/**
	 * This methods do get mapping and redirects to the respective page according to the error request
	 * @param request
	 * @return page name
	 */
	@RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
		 Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		    
		    if (status != null) {
		        Integer statusCode = Integer.valueOf(status.toString());
		    
		        if(statusCode == HttpStatus.NOT_FOUND.value()) {
		            return "error/404";
		        }
		        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
		            return "error/something_went_wrong";
		        }
		        else if(statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
		            return "error/something_went_wrong";
		        }
		    }
		    return "error/something_went_wrong";
    }
	
	/**
	 * @override method to show error path
	 */
	@Override
	public String getErrorPath() {
		return null;
	}

}
