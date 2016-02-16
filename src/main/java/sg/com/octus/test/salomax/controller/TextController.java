package sg.com.octus.test.salomax.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import sg.com.octus.test.salomax.common.exception.TextException;
import sg.com.octus.test.salomax.common.to.TextTO;
import sg.com.octus.test.salomax.model.service.TextService;

/**
 * Text controller to REST API.
 * 
 * @author salomax
 */
@RestController
public class TextController {

	/**
	 * Component service layer to text.
	 */
	@Autowired
	private TextService textService;

	/**
	 * Search for texts by a partial text value (no idempotent).
	 * 
	 * @return List of {@code Text}
	 */
    @RequestMapping(
    		path="/text",
    		method=RequestMethod.GET,
    		produces="application/json")
    public @ResponseBody List<TextTO> list() {
        return this.textService.list();
    }
    
	/**
	 * Save (Insert/update) a text object (idempotent).
	 * 
	 * @param text Text.
	 * @return Text saved.
	 */
    @RequestMapping(
    		path="/text/{id}",
    		method=RequestMethod.PUT,
    		consumes="application/json",
    		produces="application/json")
    public void put(@PathVariable("id") String id, TextTO text) {
    	text.setId(id);
        this.textService.save(text);
    }	

	/**
	 * Save (Insert/update) a text object.
	 * 
	 * @param text Text.
	 * @return Text saved.
	 */
    @RequestMapping(
    		path="/text",
    		method=RequestMethod.POST,
    		consumes="application/json",
    		produces="application/json")
    @ExceptionHandler(TextException.class)
    public TextTO post(@RequestBody TextTO text) {
        return this.textService.save(text);
    }	

	/**
	 * Delete a {@code Text} by id.
	 * 
	 * @param id {@code Text} id.
	 */
    @RequestMapping(
    		path="/text/{id}",
    		method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) {
    	this.textService.delete(id);
    }	
    
}
