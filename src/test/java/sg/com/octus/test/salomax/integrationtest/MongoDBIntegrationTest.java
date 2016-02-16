package sg.com.octus.test.salomax.integrationtest;

import static com.mongodb.client.model.Filters.eq;

import java.util.UUID;

import org.bson.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Integration test for mongodb (environment requires).
 * 
 * MongoDB CRUD example {@link https://docs.mongodb.org/getting-started/java}.
 * 
 * About integration tests {@link http://zeroturnaround.com/rebellabs/the-correct-way-to-use-integration-tests-in-your-build-process/}.
 * 
 * @author salomax
 */
public class MongoDBIntegrationTest {
	
	/**
	 * Default value.
	 */
	private static String DOC_VALUE =  
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit";
	
	/**
	 * Drop simple collection after integration test.
	 */
	@After
	public void dropSimpleCollection() {
		
		try {
			// Get connection
			MongoDatabase db = getConnection();
			// Assert not null
			Assert.assertNotNull(db);
			// Create a simple collection
			db.getCollection("simples").drop();
		} catch (MongoException e) {
			Assert.fail();
		}		
	}
	
	/**
	 * Test MongoDB connection.
	 */
	@Test
	public void testConnection() {
		try {
			// Get connection
			MongoDatabase db = getConnection();
			// Assert not null
			Assert.assertNotNull(db);
		} catch (MongoException e) {
			Assert.fail();
		}
	}
	
	/**
	 * Open connection to mongodb.
	 * @return Mongo database instance.
	 */
	private MongoDatabase getConnection() {
		// Get mongo client
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient();
		// Connect to "test" database
		MongoDatabase db = mongoClient.getDatabase("test");
		// Return
		return db;
	}
	
	/**
	 * Test MongoDB insert document.
	 */
	@Test
	public void insertSimpleDocument() {

		try {
			
			// Get connection
			MongoDatabase db = getConnection();
			// Assert not null
			Assert.assertNotNull(db);
			
			// Create a simple collection
			db.getCollection("simples").insertOne(
					new Document("key", UUID.randomUUID().toString()));
			
		} catch (MongoException e) {
			Assert.fail();
		}
		
	}

	/**
	 * Test MongoDB query document.
	 */
	@Test
	public void querySimpleDocument() {

		try {
			
			// Get connection
			MongoDatabase db = getConnection();
			// Assert not null
			Assert.assertNotNull(db);
			
			// Create a simple collection
			String key = UUID.randomUUID().toString();
		
			db.getCollection("simples").insertOne(
					new Document("key", key).append("value", DOC_VALUE));
			
			// Get iterable for query
			FindIterable<Document> iterable = 
					db.getCollection("simples").find(eq("key", key));
			
			// Assert answer
			Document document = iterable.first();
			Assert.assertNotNull(document);
			Assert.assertEquals(document.getString("key"), key);
			Assert.assertEquals(document.getString("value"), DOC_VALUE);
			
		} catch (MongoException e) {
			Assert.fail();
		}
		
	}

	/**
	 * Test MongoDB update document.
	 */
	@Test
	public void updateSimpleDocument() {
		
		try {
			
			// Get connection
			MongoDatabase db = getConnection();
			// Assert not null
			Assert.assertNotNull(db);
			
			// Create a simple collection and insert
			String key = UUID.randomUUID().toString();
			db.getCollection("simples").insertOne(
					new Document("key", key).append("value", DOC_VALUE));

			// Get iterable for query
			FindIterable<Document> iterable = 
					db.getCollection("simples").find(eq("key", key));
			
			// Assert answer
			Document document = iterable.first();
			Assert.assertNotNull(document);
			Assert.assertEquals(document.getString("key"), key);
			Assert.assertEquals(document.getString("value"), DOC_VALUE);
			
			// Update
			String newValue = "In vitae luctus libero. Suspendisse potenti";
			UpdateResult updateResult=  db.getCollection("simples").updateOne(eq("key", key), 
					new Document("$set", new Document("value", newValue)));

			// Must be "1"
			Assert.assertEquals(1, updateResult.getMatchedCount());

			// Valid count must be "1"
			long count = db.getCollection("simples").count(eq("key", key));
			Assert.assertEquals(count, 1);
			
			// Get iterable for query
			iterable = db.getCollection("simples").find(eq("key", key));
			
			// Assert answer
			document = iterable.first();
			Assert.assertNotNull(document);
			Assert.assertEquals(document.getString("key"), key);
			Assert.assertEquals(document.getString("value"), newValue);			
			
		} catch (MongoException e) {
			Assert.fail();
		}
		
	}
	
	/**
	 * Test MongoDB remove document.
	 */
	@Test
	public void removeSimpleDocument() {

		try {
			
			// Get connection
			MongoDatabase db = getConnection();
			// Assert not null
			Assert.assertNotNull(db);
			
			// Create a simple collection
			String key = UUID.randomUUID().toString();
			db.getCollection("simples").insertOne(
					new Document("key", key).append("value", DOC_VALUE));
			
			// Get iterable for query
			FindIterable<Document> iterable = 
					db.getCollection("simples").find(eq("key", key));
			
			// Assert answer
			Document document = iterable.first();
			Assert.assertNotNull(document);
			Assert.assertEquals(document.getString("key"), key);
			
			// Remove
			DeleteResult deleteResult = 
					db.getCollection("simples").deleteMany(
							new Document("key", key));

			// Assert answer
			Assert.assertNotNull(document);
			Assert.assertEquals(deleteResult.getDeletedCount(), 1);
			
		} catch (MongoException e) {
			Assert.fail();
		}
		
	}
	
}
