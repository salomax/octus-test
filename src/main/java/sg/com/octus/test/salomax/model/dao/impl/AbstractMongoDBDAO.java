package sg.com.octus.test.salomax.model.dao.impl;

import java.util.UUID;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Abstract class to MongoDB implementation DAOs.
 * 
 * @author salomax
 */
public abstract class AbstractMongoDBDAO {

	/**
	 * Generate unique Id with UUID strategy, odds of creating 
	 * a few tens of trillions of UUIDs in a year and having one duplicate,
	 * moreover, the id will be test with unique key index at insert moment.
	 * 
	 * @see https://en.wikipedia.org/wiki/Universally_unique_identifier#Random_UUID_probability_of_duplicates
	 * @return Unique id.
	 */
	protected String generateId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Open connection to mongodb.
	 * @return MongoDB database instance.
	 */
	protected MongoDatabase getConnection() {
		// Get mongo client
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
		// Connect to "test" database
		MongoDatabase db = mongoClient.getDatabase("octus-test");
		// Return
		return db;
	}
	
}
