package ai.revere.monitor.utils;

import ai.revere.monitor.Resources;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class CryptUtils {

	  private static final String SECRET_KEY = getSecretKey();
	  private static final String IV = getIV();
	  private static final String CIPHER_TYPE = "AES/CBC/PKCS5PADDING";

	  private static IvParameterSpec ivParameterSpec;
	  private static SecretKeySpec secretKeySpec;

	  private static DynamoDB dynamoDB;

		static {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(Resources.getAwsAccessCredentialsFilePath()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			BasicAWSCredentials awsCreds = new BasicAWSCredentials(properties.getProperty("accessKey"), properties.getProperty("secretKey"));
			AmazonDynamoDB client = AmazonDynamoDBAsyncClientBuilder.standard()
					.withRegion(Regions.fromName(properties.getProperty("defaultRegion")))
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds))
					.build();

			dynamoDB = new DynamoDB(client);

		}

	  static {
		  try {
			  secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
			  ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
		  } catch (Exception ex) {
			  ex.printStackTrace();
		  }
	  }

	private static synchronized Item getDynamoConfigItem(String configName) {

		Table table = dynamoDB.getTable("global-config-app");

		InetAddress ip = null;
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		String serverIP = ip.getHostAddress();

		GetItemSpec spec = new GetItemSpec().withPrimaryKey("configName", configName, "ipAddress", serverIP);

		Item item = null;
		try {
			item = table.getItem(spec);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return item;
	}


	/**
	 * This method gets the config value from the item (item retrieved from getDynamoConfigItem)
	 */

	private static synchronized String getAppConfigValue(Item item) {

		String configValue = "";
		if (item != null) {
			try {
				configValue = (String) item.get("configValue");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return configValue;
	}

	private static String getSecretKey() {
		return getAppConfigValue(getDynamoConfigItem("aes.key"));
	}

	private static String getIV() {
		return getAppConfigValue(getDynamoConfigItem("aes.iv"));
	}


	  /*
	   * Encrypt data
	   *
	   * @param text data for encryption
	   *
	   * @return encrypted data
	   */

	  public static String encrypt(final String text) throws Exception {
		  Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
		  cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		  byte[] encrypted = cipher.doFinal(text.getBytes());
		  return encodeBase64String(encrypted);
	  }

	 /*
      * Decrypt data
      *
      * @param encrypted data for decryption
	  *
	  * @return decrypted data
      */
	  public static String decrypt(final String hash) throws Exception {
		  Cipher cipher = Cipher.getInstance(CIPHER_TYPE);
		  cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		  byte[] decryptedBytes = cipher.doFinal(decodeBase64(hash));
		  return new String(decryptedBytes);
	  }
}
