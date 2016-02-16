package sg.com.octus.test.salomax.common.to;

/**
 * Transfer object design pattern applied to text. 
 * 
 * @author salomax
 */
public class TextTO extends TransferObject {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5900924458247997441L;

	/**
	 * Unique Id.
	 */
	private String id;
	
	/**
	 * Text.
	 */
	private String text;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
}
