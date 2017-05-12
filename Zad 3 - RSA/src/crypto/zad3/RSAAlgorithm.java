package crypto.zad3;

import java.math.BigInteger;
import java.util.Arrays;

public class RSAAlgorithm {

    private static BigInteger encrypt(BigInteger m, RSAKey key) {
        BigInteger n = key.getModulus();
        BigInteger e = key.getExponent();
        BigInteger c = m.modPow(e, n);
        return c;
    }

    private static BigInteger decrypt(BigInteger c, RSAKey key) {
        BigInteger n = key.getModulus();
        BigInteger d = key.getExponent();
        BigInteger m = c.modPow(d, n);
        return m;
    }

    private static byte[] PadMessage(byte[] message, int keysize) {
        /*
        Format:
        0x01 <dowolna ilość 0x00> 0x01 <wiadomość>
         */
        if (message.length > keysize - 2) {
            throw new IllegalArgumentException();
        }
        byte[] res = new byte[keysize];
        int zeros = keysize - 2 - message.length;
        res[0] = 0x01;
        res[zeros + 1] = 0x01;
        System.arraycopy(message, 0, res, zeros + 2, message.length);
        return res;
    }

    private static byte[] UnPadMessage(byte[] message, int keysize) {
        /*
        Format:
        0x01 <dowolna ilość 0x00> 0x01 <wiadomość>
         */
        if (message[0] != 0x01) {
            throw new IllegalArgumentException();
        }
        int i = 1;
        while (i < keysize && message[i] == 0x00) {
            i++;
        }

        if (i == keysize) {
            throw new IllegalArgumentException();
        }

        if (message[i] != 0x01) {
            throw new IllegalArgumentException();
        } else {
            i++;
        }
        byte[] unpadded = Arrays.copyOfRange(message, i, keysize);
        return unpadded;
    }

    public static byte[] encrypt(byte[] message, RSAKey key) {
        int ksize = key.getModulus().bitLength() / 8;
        int blocksize = ksize;
        int maxmsglen = ksize - 2;
        int count = (message.length % maxmsglen != 0) ? message.length / maxmsglen + 1 : message.length / maxmsglen;
        byte[] output = new byte[blocksize * count];
        int inpos = 0;
        int outpos = 0;
        int msglen;

        for (int i = 0; i < count; i++) {
            if (message.length - i * maxmsglen < maxmsglen) {
                msglen = message.length - i * maxmsglen;
            } else {
                msglen = maxmsglen;
            }
            byte[] block = PadMessage(Arrays.copyOfRange(message, inpos, inpos + msglen), blocksize);
            BigInteger m = Utils.ByteArrayToBigInt(block);
            BigInteger c = encrypt(m, key);
            byte[] cipher = Utils.BigIntToByteArray(c);
            System.arraycopy(cipher, 0, output, outpos, blocksize);
            inpos += msglen;
            outpos += blocksize;
        }
        return output;
    }

    public static byte[] decrypt(byte[] cipher, RSAKey key) {
        int ksize = key.getModulus().bitLength() / 8;
        int blocksize = ksize;
        if (cipher.length % blocksize != 0) {
            throw new IllegalArgumentException();
        }
        int count = cipher.length / blocksize;
        byte[] output = null;
        int inpos = 0;
        int outpos = 0;
        
        for (int i = 1; i <= count; i++) {
            byte[] block = Arrays.copyOfRange(cipher, inpos, inpos + blocksize);
            BigInteger c = Utils.ByteArrayToBigInt(block);
            BigInteger m = decrypt(c, key);
            byte[] msg = Utils.BigIntToByteArray(m);
            byte[] unpadded = UnPadMessage(msg, blocksize);
            if (output != null) {
                output = Arrays.copyOf(output, outpos + unpadded.length);
                System.arraycopy(unpadded, 0, output, outpos, unpadded.length);
                outpos += unpadded.length;
                inpos += blocksize;
            } else {
                output = Arrays.copyOf(unpadded, unpadded.length);
                outpos += unpadded.length;
                inpos += blocksize;
            }
        }
        return output;
    }
}
