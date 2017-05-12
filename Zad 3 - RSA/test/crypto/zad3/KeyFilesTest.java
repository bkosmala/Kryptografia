package crypto.zad3;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KeyFilesTest {

    @Test
    public void testReadKey() {
        try {
            System.out.println("readKey");
            String filename = "testKeyFile.txt";
            RSAKey expResult = new RSAKey(new BigInteger("12345678910111213141516171819", 16), new BigInteger("16171819", 16));
            List<String> lines = new ArrayList<>();
            lines.add("12345678910111213141516171819");
            lines.add("16171819");

            Files.write(Paths.get(filename), lines);
            RSAKey result = KeyFiles.readKey(filename);
            Files.delete(Paths.get(filename));
            assertEquals(expResult, result);
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteKey() {
        try {
            System.out.println("writeKey");
            String filename = "testKeyFile.txt";
            RSAKey key = new RSAKey(new BigInteger("12345678910111213141516171819", 16), new BigInteger("16171819", 16));
            List<String> expResult = new ArrayList<>();
            expResult.add("12345678910111213141516171819");
            expResult.add("16171819");
            
            KeyFiles.writeKey(filename, key);
            List<String> lines = Files.readAllLines(Paths.get(filename));
            Files.delete(Paths.get(filename));
            assertEquals(expResult, lines);
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }

}