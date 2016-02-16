package sg.com.octus.test.salomax.security.filter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

/**
 * A handling example to create and to valid a token.
 * 
 * @see https://github.com/philipsorst/angular-rest-springsecurity/blob/master/
 *      src/main/java/net/dontdrinkandroot/example/angularrestspringsecurity/
 *      rest/TokenUtils.java
 * 
 * @author salomax
 */
public class TokenHelper {

	/**
	 * Some constant to add a key to encode.
	 */
	public static final String MAGIC_KEY = "obfuscate";

	/**
	 * Create token (not yet encoded).
	 */
	public static String createToken(UserDetails userDetails) {

		// An hour
		long expires = System.currentTimeMillis() + 1000L * 60 * 60;

		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(userDetails.getUsername());
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(TokenHelper.computeSignature(userDetails, expires));

		return tokenBuilder.toString();
	}

	/**
	 * Create token's signature.
	 */
	public static String computeSignature(UserDetails userDetails, long expires) {

		StringBuilder signatureBuilder = new StringBuilder();

		signatureBuilder.append(userDetails.getUsername());
		signatureBuilder.append(":");
		signatureBuilder.append(expires);
		signatureBuilder.append(":");
		signatureBuilder.append(userDetails.getPassword());
		signatureBuilder.append(":");
		signatureBuilder.append(TokenHelper.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}

	/**
	 * Split token and pick up the username.
	 */
	public static String getUserNameFromToken(String authToken) {

		if (null == authToken) {
			return null;
		}

		String[] parts = authToken.split(":");

		return parts[0];
	}

	/**
	 * Validate token.
	 */
	public static boolean validateToken(String authToken, UserDetails userDetails) {
		String[] parts = authToken.split(":");
		long expires = Long.parseLong(parts[1]);
		String signature = parts[2];

		if (expires < System.currentTimeMillis()) {
			return false;
		}

		return signature.equals(TokenHelper.computeSignature(userDetails, expires));
	}
}
