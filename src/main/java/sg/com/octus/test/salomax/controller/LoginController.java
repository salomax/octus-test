package sg.com.octus.test.salomax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.com.octus.test.salomax.common.exception.TextException;
import sg.com.octus.test.salomax.common.to.TokenTO;
import sg.com.octus.test.salomax.model.dao.UserDAO;
import sg.com.octus.test.salomax.security.filter.TokenHelper;

/**
 * Login controller to REST API.
 * 
 * @author salomax
 */
@RestController
public class LoginController {
	
	/**
	 * Authentication manager.
	 */	
	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;
	
	/**
	 * User data access object.
	 */
	@Autowired
	private UserDAO userDAO;

	/**
	 * Login.
	 */
    @RequestMapping(
    		path="/login",
    		method=RequestMethod.POST,
    		consumes="application/x-www-form-urlencoded",
    		produces="application/json")
    @ExceptionHandler(TextException.class)
    public TokenTO authenticate(@RequestParam("username") String username, 
    		@RequestParam("password") String password) throws TextException {
    	
    	// Create authentication
    	UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = this.userDAO.loadUserByUsername(username);

		return new TokenTO(TokenHelper.createToken(userDetails));
    }	
    
   
}
