package sg.com.octus.test.salomax.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Unauthorized (403) entry point.
 * 
 * @author salomax
 */
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

	/**
	 * Method to send 403 HTTP error code.
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}