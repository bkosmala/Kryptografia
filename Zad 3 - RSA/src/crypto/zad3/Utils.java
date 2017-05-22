package crypto.zad3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static byte[] BigIntToByteArray(BigInteger n) {
        byte[] array = n.toByteArray();
        int bitlen = n.bitLength();
        if (bitlen % 8 == 0) {
            // in this case BigInteger will add aditional byte and put sign bit to it
            if (array[0] != 0) {
                throw new IllegalArgumentException("The argument must be non negative integer");
            } else {
                // sign bit can here savely be skipped
                array = Arrays.copyOfRange(array, 1, array.length);
            }
        } else {
            // here BigInteger will add addional bit to the first byte
            if (bitlen % 8 == 7) {
                // here additional bit will be put into sign bit of a byte, possibly giving negative value
                if (array[0] < 0) {
                    throw new IllegalArgumentException("The argument must be non negative integer");
                }
            }
        }
        return array;
    }

    public static BigInteger ByteArrayToBigInt(byte[] tab) {
        return new BigInteger(1, tab);
    }
    
    public static List<byte[]> splitIntoBlocks(byte[] array, int blocksize) {
        List<byte[]> blocks = new ArrayList<>();
        int inpos = 0;
        int remaining = array.length;
        while (remaining > 0) {
            int len = Math.min(remaining, blocksize);
            blocks.add(Arrays.copyOfRange(array, inpos, inpos+len));
            inpos += len;
            remaining -= len;
        }
        return blocks;
    }
    
    public static byte[] concatBlocks(List<byte[]> blocks) {
        int outpos = 0;
        int len = 0;
        for (byte[] b : blocks) {
            len += b.length;
        }
        byte[] res = new byte[len];
        for (byte[] b : blocks) {
            System.arraycopy(b, 0, res, outpos, b.length);
            outpos += b.length;
        }
        return res;
    }
}
