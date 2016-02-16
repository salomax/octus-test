package sg.com.octus.test.salomax.unittest;

import static org.mockito.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import sg.com.octus.test.salomax.common.to.TextTO;
import sg.com.octus.test.salomax.controller.TextController;
import sg.com.octus.test.salomax.model.service.TextService;

/**
 * Simple CRUD Text entity unit test.
 * 
 * @author salomax
 */
public class TextControllerTestCase {
	
	/**
	 * Default text.
	 */
	private static final String DEFAULT_TEXT = 
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit";

	/**
	 * Mock service.
	 */
	@Mock
    private TextService textService;
 
	/**
	 * Text controller to inject the mocks.
	 */
    @InjectMocks
    private TextController textController;
    
    /**
     * Mock MVC.
     */
    private MockMvc mockMvc;

    /**
     * Before the tests.
     */
    @Before
    public void setup() {

        // Process annotations
        MockitoAnnotations.initMocks(this);
 
        // Setup Spring test in standalone
        this.mockMvc = MockMvcBuilders.standaloneSetup(textController).build();
 
    }
    
	/**
	 * Test list all texts persisted.	
	 */
	@Test
	public void listText() {
        
		try {
			
			// Text
			TextTO textTO = new TextTO();
			textTO.setId(UUID.randomUUID().toString());
			textTO.setText(DEFAULT_TEXT);
			
			// Text list
			List<TextTO> textList = new ArrayList<TextTO>();
			textList.add(textTO);
			
			// Mock list service
			when(this.textService.list()).thenReturn(textList);
			
			// Perform list
        	this.mockMvc.perform(get("/text")
					.accept(MediaType.APPLICATION_JSON_UTF8))
			        .andExpect(status().isOk())
			        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			        .andExpect(jsonPath("$").exists())
			        .andExpect(jsonPath("$", hasSize(1)))
	        		.andExpect(jsonPath("$[0]").exists())
	        		.andExpect(jsonPath("$[0].text").value(DEFAULT_TEXT));
	        
        	// Verify list
        	verify(this.textService, times(1)).list();
            
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}
	
	/**
	 * Test save some text.	
	 */
	@Test
	public void saveSomeText() {
        
		try {
			
			// Text
			TextTO textToInsert = new TextTO();
			textToInsert.setText(DEFAULT_TEXT);

			TextTO textToReturn = new TextTO();
			textToReturn.setId(UUID.randomUUID().toString());
			textToReturn.setText(DEFAULT_TEXT);
			
			// Mock service
			when(this.textService.save(any(TextTO.class))).thenReturn(textToReturn);
			
			// Perform post
        	this.mockMvc.perform(post("/text")
	        			.contentType(MediaType.APPLICATION_JSON)
	        			.content(textToInsert.toString())
					.accept(MediaType.APPLICATION_JSON))
			        .andExpect(status().isOk())
			        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			        .andExpect(jsonPath("$").exists())
	        		.andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
            
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}
	
	/**
	 * Test delete some text.	
	 */
	@Test
	public void deleteText() {
        
		try {
			
			// Perform post
        	this.mockMvc.perform(delete("/text/{id}", UUID.randomUUID().toString()))
			        .andExpect(status().isOk());
            
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}
	
}
