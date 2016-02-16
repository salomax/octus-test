package sg.com.octus.test.salomax.unittest;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sg.com.octus.test.salomax.common.to.TextTO;
import sg.com.octus.test.salomax.model.dao.TextDAO;
import sg.com.octus.test.salomax.model.service.TextService;
import sg.com.octus.test.salomax.model.service.impl.TextServiceImpl;

/**
 * Text service test case.
 * 
 * @author salomax
 *
 */
public class TextServiceTestCase {
	
	/**
	 * Default text.
	 */
	private static final String DEFAULT_TEXT = 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit";

	/**
	 * Text DAO.
	 */
	@Mock
    private TextDAO textDAO;
	
	/**
	 * Mock service.
	 */
	@InjectMocks
    private TextService textService;

    /**
     * Before the tests.
     */
    @Before
    public void setup() {
    	
    	// The better solution is a constructor over field injection
    	// or even use springockito to avoid that
    	// but it works to keep a simple test case
    	textService = new TextServiceImpl();

        // Process annotations
        MockitoAnnotations.initMocks(this);
 
    }
    
	/**
	 * Test list all texts persisted.	
	 */
	@Test
	public void listText() {
		
		// Text
		TextTO textTO = new TextTO();
		textTO.setId(UUID.randomUUID().toString());
		textTO.setText(DEFAULT_TEXT);
		
		// Text list
		List<TextTO> textList = new ArrayList<TextTO>();
		textList.add(textTO);
		
		// mock dao
		when(textDAO.list()).thenReturn(textList);
		
		// executing service list method
		List<TextTO> list = this.textService.list();
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() == 1);
		Assert.assertEquals(list.get(0).getText(), DEFAULT_TEXT);
    	
	}
	
	/**
	 * Test save some text.	
	 */
	@Test
	public void saveText() {
		
		// Text
		TextTO textToInsert = new TextTO();
		textToInsert.setText(DEFAULT_TEXT);

		TextTO textToReturn = new TextTO();
		textToReturn.setId(UUID.randomUUID().toString());
		textToReturn.setText(DEFAULT_TEXT);
		
		// mock dao
		when(this.textDAO.insert(same(textToInsert))).thenReturn(textToReturn);
		
		// executing service list method
		TextTO result = this.textService.save(textToInsert);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getId(), textToReturn.getId());
		Assert.assertEquals(result.getText(), textToReturn.getText());
    	
	}

	/**
	 * Test save (update) some text.	
	 */
	@Test
	public void updateText() {
		
		// Text
		TextTO textToUpdate = new TextTO();
		textToUpdate.setId(UUID.randomUUID().toString());
		textToUpdate.setText(DEFAULT_TEXT);

		TextTO textToReturn = new TextTO();
		textToReturn.setId(textToUpdate.getId());
		textToReturn.setText(DEFAULT_TEXT);
		
		// mock dao
		when(this.textDAO.update(same(textToUpdate))).thenReturn(textToReturn);
		
		// executing service list method
		TextTO result = this.textService.save(textToUpdate);
		Assert.assertNotNull(result);
		Assert.assertEquals(result.getId(), textToReturn.getId());
		Assert.assertEquals(result.getText(), textToReturn.getText());
    	
	}
	
	/**
	 * Test delete some text.	
	 */
	@Test
	public void deleteText() {

		// executing service list method
		this.textService.delete(UUID.randomUUID().toString());
    	
	}
	
}
