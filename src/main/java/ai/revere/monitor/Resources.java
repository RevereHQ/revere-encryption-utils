
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

	public static String getAwsAccessCredentialsFilePath() {
		return System.getProperty(getGroupId() + ".aws.properties");
	}

}
