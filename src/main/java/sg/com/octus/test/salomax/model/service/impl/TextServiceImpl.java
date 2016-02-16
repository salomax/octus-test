package sg.com.octus.test.salomax.model.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.com.octus.test.salomax.common.exception.TextException;
import sg.com.octus.test.salomax.common.to.TextTO;
import sg.com.octus.test.salomax.model.dao.TextDAO;
import sg.com.octus.test.salomax.model.service.TextService;

/**
 * Service implementation for texts features.
 * 
 * @author salomax
 */
@Service
public class TextServiceImpl implements TextService {
	
	/**
	 * Text DAO.
	 */
	@Autowired
	private TextDAO dao;
	
	/**
	 * Save (Insert/update) a text object.
	 * 
	 * @param text Text.
	 * @return Text saved.
	 */
	public TextTO save(TextTO text) {
		
		// Verify nulls
		if (text == null || StringUtils.isEmpty(text.getText())) {
			throw new TextException("A text is required!");
		}
		
		// Save
		if (isValidId(text.getId())) {
			return this.dao.update(text);
		} else {
			return this.dao.insert(text);
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
	 * Delete a {@code Text} by id.
	 * 
	 * @param id {@code Text} id.
	 */
	public void delete(String id) {
		this.dao.delete(id);
	}
	
	/**
	 * Search for texts by a partial text value.
	 * 
	 * @return List of {@code Text}
	 */
	public List<TextTO> list() {
		return this.dao.list();
	}

}
