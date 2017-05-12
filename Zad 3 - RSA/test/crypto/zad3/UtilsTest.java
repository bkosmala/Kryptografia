package crypto.zad3;

import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
    
    @Test
    public void testBigIntToByteArray() {
        System.out.println("BigIntToByteArray");
        BigInteger n = new BigInteger("1234567891", 16);
        byte[] expResult = new byte[] {(byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x91};
        byte[] result = Utils.BigIntToByteArray(n);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testByteArrayToBigInt() {
        System.out.println("ByteArrayToBigInt");
        byte[] tab = new byte[] {(byte)0x12, (byte)0x34, (byte)0x56, (byte)0x78, (byte)0x91};
        BigInteger expResult = new BigInteger("1234567891", 16);
        BigInteger result = Utils.ByteArrayToBigInt(tab);
        assertEquals(expResult, result);
    }
    
}
