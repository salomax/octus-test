package sg.com.octus.test.salomax.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Simple and standard exception to flow errors.
 * 
 * @author salomax
 */
@ResponseStatus(code=HttpStatus.UNPROCESSABLE_ENTITY, reason="The content sent to server was inappropriate")
public class TextException extends RuntimeException {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5327610767336001483L;

	/**
	 * Default constructor.
	 * @param message Error message.
	 */
	public TextException(String message) {
		super(message);
	}

}
