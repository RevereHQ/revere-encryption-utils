
package ai.revere.monitor;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;


@ApplicationScoped
public class Resources {

	/**
	 * Loads the project's properties from the project.properties resource.
	 */
	private static synchronized void loadProperties() throws IOException {
		if (projectProperties == null) {
			Properties temp = new Properties();
			URL url = Resources.class.getClassLoader().getResource("project.properties");
			temp.load(url.openStream());
			projectProperties = temp;
		}
	}

	/** In memory representation of project.properties. */
	private static Properties projectProperties = null;

	/**
	 * Returns the group ID as defined in the project's pom.xml.
	 *
	 * @return the group ID.
	 */
	public static String getGroupId() {
		try {
			if (projectProperties == null) {
				loadProperties();
			}
			return projectProperties.getProperty("project.group");
		} catch (IOException e) {
			return null;
		}
	}


	/*
	 * Receiving secret key for encryption
	 *
	 * @return secret key or null if not defined
	 */
	public static String getSecretKey() {
		try {
			return System.getProperty(getGroupId() + ".aes.key");
		} catch (Exception e) {
			return null;
		}
	}
	/*
	 * Receiving iv for encryption
	 *
	 * @return iv or null if not defined
	 */
	public static String getIV() {
		try {
		  return System.getProperty(getGroupId() + ".aes.iv");
		} catch (Exception e) {
			return null;
		}
	}
}
