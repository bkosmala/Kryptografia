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

    static RSAKey readKey(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        List<BigInteger> list = new ArrayList<>();
        for (Iterator<String> iterator = lines.iterator(); iterator.hasNext();) {
            list.add(new BigInteger(iterator.next(), 16));
        }
        return new RSAKey(list);

    }

    static void writeKey(String filename, RSAKey key) throws IOException {
        List<BigInteger> list = key.getValuesList();
        List<String> lines = new ArrayList<>();
        for (Iterator<BigInteger> iterator = list.iterator(); iterator.hasNext();) {
            lines.add(iterator.next().toString(16));
        }
        Files.write(Paths.get(filename), lines, StandardCharsets.UTF_8);
    }
}
