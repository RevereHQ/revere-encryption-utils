package ai.revere.monitor.utils;

import ai.revere.monitor.Resources;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public class CryptUtils {

	  private static final String SECRET_KEY = Resources.getSecretKey();
	  private static final String IV = Resources.getIV();
	  private static final String CIPHER_TYPE = "AES/CBC/PKCS5PADDING";

	  private static IvParameterSpec ivParameterSpec;
	  private static SecretKeySpec secretKeySpec;

	  static {
		  try {
			  secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
			  ivParameterSpec = new IvParameterSpec(IV.getBytes("UTF-8"));
		  } catch (Exception ex) {
			  ex.printStackTrace();
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
