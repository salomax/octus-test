package sg.com.octus.test.salomax.model.dao;

import java.util.List;

import sg.com.octus.test.salomax.common.to.TextTO;

/**
 * Text Data Access Object inteface.
 * 
 * @author salomax
 */
public interface TextDAO {

	/**
	 * Insert new text.
	 * 
	 * @param text Text to insert or update.
	 */
	public void insert(TextTO text);

	/**
	 * Update new text.
	 * 
	 * @param text Text to insert or update.
	 */
	public void update(TextTO text);
	
	/**
	 * Get a collection of texts.
	 * 
	 * @return Texts.
	 */
	public List<TextTO> list();

	/**
	 * Delete text.
	 * 
	 * @param id Text id.
	 */
	public void delete(String id);

}
