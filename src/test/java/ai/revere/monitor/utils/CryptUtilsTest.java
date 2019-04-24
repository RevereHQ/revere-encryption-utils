package ai.revere.monitor.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
}

