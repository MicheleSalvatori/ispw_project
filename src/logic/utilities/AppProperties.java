package logic.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
	private static final String PREFIX = "system.";
	private static AppProperties instance;
	private Properties prop;

	private AppProperties() {
		try (InputStream input = AppProperties.class.getResourceAsStream("../system.properties")) {
			prop = new Properties();
			prop.load(input);
			
		} catch (IOException e) {
			// TODO
		}
	}

	public static AppProperties getInstance() {
		if (instance == null)
			instance = new AppProperties();

		return instance;
	}

	public String getProperty(String key) {
		return prop.getProperty(PREFIX + key);
	}
	
	public void setProperty(String key, String value) {
		prop.setProperty(PREFIX + key, value);
	}
}
