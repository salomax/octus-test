package sg.com.octus.test.salomax.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.context.annotation.Configuration;

import sg.com.octus.test.salomax.common.exception.TextException;

/**
 * Util class to app configuration.
 * 
 * @author salomax
 */
@Configuration
public class Configurator {
	

	private static Properties PROPERTIES;
	
	public static String get(String key) {
		
		if (PROPERTIES == null) {
			
			InputStream inputStream = Configurator.class.getClassLoader()
					.getResourceAsStream("octus-salomax-test.properties");
			
			PROPERTIES = new Properties();
			try {
				PROPERTIES.load(inputStream);
			} catch (IOException e) {
				throw new TextException("Is not possible to load properties file" + e.getMessage());
			}
			
		}
		
		return PROPERTIES.getProperty(key);
	}


	
}
