package ai.revere.monitor.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class CryptUtils {

		protected static final Logger cryptUtilsLogger  = Logger.getLogger(CryptUtils.class.getName());
		private static final String CIPHER_TYPE = "AES/CBC/PKCS5PADDING";

		private static Properties props;

		/* load the secret key and iv from config properties file  located in the main/src/resources folder */

		static {
			props = new Properties();
			try
			{
				props.load(CryptUtils.class.getResourceAsStream("/config.properties"));
			} catch (IOException e)  {
				e.printStackTrace();
			}
		}

		private static IvParameterSpec ivParameterSpec;
		private static SecretKeySpec secretKeySpec;

		private static final String SECRET_KEY = getSecretKey();
		private static final String IV = getIV();

		static {
			try {

			  cryptUtilsLogger.info("SECRET KEY = " + SECRET_KEY);
			  cryptUtilsLogger.info("IV = " + IV);

			  secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
			  ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/*
		 * Receiving secret key for encryption
		 *
		 * @return secret key or null if not defined
		 */

		private static String getSecretKey() {
			try {
				return props.getProperty("aes.key");
			} catch (Exception e) {
				return "";
			}
		}

		/*
		 * Receiving iv for encryption
		 *
		 * @return iv or null if not defined
		 */
		private static String getIV() {
			try {
				return props.getProperty("aes.iv");
			} catch (Exception e) {
				return "";
			}
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
