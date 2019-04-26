package ai.revere.monitor.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@RunWith(MockitoJUnitRunner.class)

public class CryptUtilsTest {

    @Test
    public void testEncryption() {
        String textUnencrypted = "THIS is a very long piece of text ....";
        try {
            String textEncrypted = CryptUtils.encrypt(textUnencrypted);
            String textEncryptedDecrypted = CryptUtils.decrypt(textEncrypted);
            Assert.assertEquals(textUnencrypted, textEncryptedDecrypted);
        } catch (Exception ex) {

        }
    }

    @Test
    public void testDecryption() {
        String textUnencrypted = "THIS is a very long piece of text ....";

        try {
            String textEncrypted = CryptUtils.encrypt(textUnencrypted);
            String textEncryptedDecrypted = CryptUtils.decrypt(textEncrypted);
            String textEncryptedDecryptedEncrypted = CryptUtils.encrypt(textEncryptedDecrypted);

            Assert.assertEquals(textEncrypted, textEncryptedDecryptedEncrypted);
        } catch (Exception ex) {

        }

    }

    /* for advanced AES encryption  -- setting our own key, iv */

    @Test
    public void testSettingAESKeyandIV() {

        try {

            // key has to be 16, 24 or 32 characters

            String aesKey = "123456789012345612345678";

            CryptUtils.cryptUtilsLogger.info("aes key string to use: " + aesKey);
            CryptUtils.cryptUtilsLogger.info("aes key string length (in bytes) =  " + aesKey.getBytes("UTF-8").length);

            // this needs be a string of 16 characters

            String ivBytes = "1234567890123456";

            CryptUtils.cryptUtilsLogger.info("aes iv string to use =  " + ivBytes);
            CryptUtils.cryptUtilsLogger.info("aes iv string length (in bytes) =  " + ivBytes.getBytes("UTF-8").length);

            SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getBytes("UTF-8"), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes.getBytes("UTF-8"));

            String textToEncrypt = "ThisIsATest";

            CryptUtils.cryptUtilsLogger.info("text to encrypt = " + textToEncrypt);

            // encryption
            Cipher cipher1 = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher1.init(1, secretKeySpec, ivParameterSpec);
            byte[] encrypted = cipher1.doFinal(textToEncrypt.getBytes());

            String encryptedTextAsString = org.apache.commons.codec.binary.Base64.encodeBase64String(encrypted);
            CryptUtils.cryptUtilsLogger.info("text encrypted = " + encryptedTextAsString);

            // decryption

            Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher2.init(2, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher2.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(encryptedTextAsString));

            String decryptedBytsAsString = new String(decryptedBytes);
            CryptUtils.cryptUtilsLogger.info("text decrypted = " + decryptedBytsAsString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

