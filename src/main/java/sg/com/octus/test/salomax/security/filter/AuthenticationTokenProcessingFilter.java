package sg.com.octus.test.salomax.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

/**
 * A handling example to create and to valid a token.
 * 
 * @see https://github.com/philipsorst/angular-rest-springsecurity/blob/master/
 *      src/main/java/net/dontdrinkandroot/example/angularrestspringsecurity/
 *      rest/AuthenticationTokenProcessingFilter.java
 * 
 * @author salomax
 */
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	/**
	 * User service.
	 */
	private final UserDetailsService userService;

	public AuthenticationTokenProcessingFilter(UserDetailsService userService) {
		this.userService = userService;
	}

	/**
	 * Filter the request to check the authentication and authorization.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String authToken = this.extractAuthTokenFromRequest(httpRequest);
		String userName = TokenHelper.getUserNameFromToken(authToken);

		if (userName != null) {

			UserDetails userDetails = this.userService.loadUserByUsername(userName);

			if (TokenHelper.validateToken(authToken, userDetails)) {

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * Extract token from request header.
	 */
	private String extractAuthTokenFromRequest(HttpServletRequest httpRequest) {

		String authToken = httpRequest.getHeader("X-Auth-Token");

		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}
	
}