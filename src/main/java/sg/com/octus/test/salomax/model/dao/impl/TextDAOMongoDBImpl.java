package sg.com.octus.test.salomax.model.dao.impl;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import sg.com.octus.test.salomax.common.exception.TextException;
import sg.com.octus.test.salomax.common.to.TextTO;
import sg.com.octus.test.salomax.model.dao.TextDAO;

/**
 * MongoDB implementation for TextDAO.
 * 
 * @author salomax
 */
@Repository
public class TextDAOMongoDBImpl extends AbstractMongoDBDAO implements TextDAO {
	
	/**
	 * Logging.
	 */
	private final static Logger LOG = Logger.getLogger(
			TextDAOMongoDBImpl.class.getCanonicalName());
	
	/**
	 * Default constructor.
	 */
	public TextDAOMongoDBImpl() {
		// Ensure index id is created
		getTextCollection().createIndex(new BasicDBObject("id", 1), 
				new IndexOptions().unique(true));
	}
	
	/**
	 * Get texts collection.
	 * 
	 * @return Texts collections.
	 */
	public MongoCollection<Document> getTextCollection() {
		// Get connection
		MongoDatabase database = getConnection();
		// Return collection
		return database.getCollection("texts");
	}
	
	/**
	 * Insert new text.
	 * 
	 * @param id Unique id for text.
	 * @param text Text to insert or update.
	 */
	public void insert(TextTO text) {

		LOG.finest(String.format("Insert the Text: %s", text));
			
		//Generate id
		text.setId(generateId());
			
		// Insert new one
		getTextCollection().insertOne(getDocumentByTextTO(text));
		
		LOG.finest(String.format("Inserted successful Text: %s", text));
		
	}
	
	
	/**
	 * Update existent text.
	 * 
	 * @param id Unique id for text.
	 * @param text Text to insert or update.
	 */
	public void update(TextTO text) {

		LOG.finest(String.format("Update the Text: %s", text));
		
		// Simple consist to id
		if (isValidId(text.getId())) {

			// Update text of current text id
			UpdateResult update = getTextCollection().updateOne(eq("id", text.getId()), 
					new Document("$set", new Document("text", text.getText())));

			// Validation
			if (update.getMatchedCount() == 0) {
				throw new TextException("No text was updated!");
			}
			
			LOG.finest(String.format("Updated successful Text: %s", text));
			
		}
		
	}

	/**
	 * Get a collection of texts.
	 * 
	 * @return Texts.
	 */
	public List<TextTO> list() {
		
		final List<TextTO> list = new ArrayList<>();
		
		// Search
		FindIterable<Document> iterable = getTextCollection().find();
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		    	list.add(getTextTOByDocument(document));
		    }
		});
		
		return list;
	}

	/**
	 * Delete text.
	 * 
	 * @param id Text id.
	 */
	public void delete(String id) {
		
		LOG.finest(String.format("Delete the Text: %s", id));
		
		// Simple consist to id
		if (isValidId(id)) {
			
			// Delete
			DeleteResult deleteResult = getTextCollection()
					.deleteOne(eq("id", id));
			
			// Validation
			if (deleteResult.getDeletedCount()  == 0) {
				throw new TextException("No text was deleted!");
			}
			
		}
		
	}

	/**
	 * Id is valid (not null and not empty)?
	 * 
	 * @param id Text id.
	 * @return if it's not null and not empty?
	 */
	private boolean isValidId(String id) {
		return id != null && !StringUtils.isEmpty(id);
	}

	/**
	 * Build {@code Document} by {@code TextTO}.
	 * @param text {@code TextTO} to be converted in a {@code Document}.
	 * @return {@code Document} created by {@code TextTO}. 
	 */
	private Document getDocumentByTextTO(TextTO text) {
		return new Document("id", text.getId()).append("text", text.getText());
	}


	/**
	 * Transforms a {@code Document} (MongoDB) to a {@code TextTO}.
	 * @param document {@code Document} from MongoDB represents a TextTO.
	 * @return A new {@code TextTO}.
	 */
	private TextTO getTextTOByDocument(Document document) {
		TextTO textTO = new TextTO();
		textTO.setId(document.getString("id"));
		textTO.setText(document.getString("text"));
		return textTO;
	}
	
}
