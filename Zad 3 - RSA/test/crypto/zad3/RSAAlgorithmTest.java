package crypto.zad3;

import java.nio.charset.StandardCharsets;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RSAAlgorithmTest {
    
    private static RSAKey privateKey;
    private static RSAKey publicKey;
    
    public RSAAlgorithmTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        RSAKeysGenerator kg = new RSAKeysGenerator();
        kg.generateKeys(1024);
        privateKey = kg.getPrivateKey();
        publicKey = kg.getPublicKey();
    }
    
    @Test
    public void testEncryptDecrypt() {
        System.out.println("encrypt/decrypt");
        byte[] message = "Wiadomość".getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = RSAAlgorithm.encrypt(message, publicKey);
        byte[] decrypted = RSAAlgorithm.decrypt(encrypted, privateKey);
        assertArrayEquals(message, decrypted);
    }
}
