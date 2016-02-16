package sg.com.octus.test.salomax.common.to;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract class to transfer objects class pattern.
 * 
 * @author salomax
 */
public abstract class TransferObject implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1338470097317509934L;
	
	/**
	 * JSON serializar mapper.
	 */
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * Retona o transfer object no padrão JSON para facilitar a visualização.
	 */
	@Override
	public String toString() {
		try {
			return MAPPER.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Não foi possível realizar toString() para formato JSON!", e);
		}
	}

}
