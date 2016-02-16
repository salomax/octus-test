package sg.com.octus.test.salomax.model.service;

import java.util.List;

import sg.com.octus.test.salomax.common.to.TextTO;

/**
 * Text service (Business Component).
 * 
 * @author salomax
 */
public interface TextService {

	/**
	 * Save (Insert/update) a text object.
	 * 
	 * @param text Text.
	 * @return Text saved.
	 */
	TextTO save(TextTO text);

	/**
	 * Delete a {@code Text} by id.
	 * 
	 * @param id {@code Text} id.
	 */
	void delete(String id);
	
	/**
	 * Search for texts by a partial text value.
	 * 
	 * @return List of {@code Text}
	 */
	List<TextTO> list();
	
}
