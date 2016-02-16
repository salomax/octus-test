package sg.com.octus.test.salomax.model.dao.impl;

import static com.mongodb.client.model.Filters.eq;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import sg.com.octus.test.salomax.model.dao.UserDAO;

/**
 * Simple implementation to resolve security aspects.
 * 
 * @author salomax
 */
public class UserDAOImpl extends AbstractMongoDBDAO implements UserDAO {
	
	/**
	 * Constructor.
	 */
	public UserDAOImpl(PasswordEncoder passwordEncoder) {
		// Create User "user" at startup
		if (getUserCollection().count(eq("username", "user")) == 0) {
			getUserCollection().insertOne(new Document("username", "user")
					.append("password", passwordEncoder.encode("user")));
		}
	}
	
	/**
	 * Get texts collection.
	 * 
	 * @return Texts collections.
	 */
	public MongoCollection<Document> getUserCollection() {
		// Get connection
		MongoDatabase database = getConnection();
		// Return collection
		return database.getCollection("users");
	}
	
	/**
	 * Simple method to get user by username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Check is username os not empty
		if (!StringUtils.isNotEmpty(username)) {
			throw new UsernameNotFoundException(String.format("User is required!", username));
		}
		
		// Verify is username is persisted
		long count = getUserCollection().count(eq("username", username));
		if (count == 0) {
			throw new UsernameNotFoundException(String.format("User %s not found!", username));
		}
		
		// Get user's password
		String password = getUserCollection().find(
				eq("username", username)).first().getString("password");

		// Create a user details helper's class to the user
		return new UserDetailsHelper(username, password);
	}

}

