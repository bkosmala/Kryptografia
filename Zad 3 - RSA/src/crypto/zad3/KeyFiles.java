package crypto.zad3;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyFiles {
    
    private static final Logger L = Logger.getLogger(KeyFiles.class.getName());

    static RSAKey readKey(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
            List<BigInteger> list = new ArrayList<>();
            for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
                list.add(new BigInteger(iterator.next(), 16));
            }
            return new RSAKey(list);
        } catch (IOException ex) {
            L.log(Level.SEVERE, "Failed to read " + filename, ex);
            return null;
        }
    }

    static void writeKey(String filename, RSAKey key) {
        try {
            List<BigInteger> list = key.getValuesList();
            List<String> lines = new ArrayList<>();
            for (Iterator<BigInteger> iterator = list.iterator(); iterator.hasNext();) {
                lines.add(iterator.next().toString(16));
            }
            Files.write(Paths.get(filename), lines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            L.log(Level.SEVERE, "Failed to write file " + filename, ex);
        }
    }
}