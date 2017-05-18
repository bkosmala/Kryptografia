package crypto.zad3;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static byte[] packMessage(byte[] message, int keysize) {
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

    private static byte[] unpackMessage(byte[] message, int keysize) {
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
        List<byte[]> blocks = Utils.splitIntoBlocks(message, maxmsglen);
        
        for (byte[] block : blocks) {
            byte[] packed = packMessage(block, blocksize);
            BigInteger m = Utils.ByteArrayToBigInt(packed);
            BigInteger c = encrypt(m, key);
            byte[] encrypted = Utils.BigIntToByteArray(c);
            blocks.set(blocks.indexOf(block), encrypted);
        }
        
        return Utils.concatBlocks(blocks);
    }

    public static byte[] decrypt(byte[] cipher, RSAKey key) {
        int ksize = key.getModulus().bitLength() / 8;
        int blocksize = ksize;
        if (cipher.length % blocksize != 0) {
            throw new IllegalArgumentException("Cipher lenght is not multiple of key size");
        }
        List<byte[]> blocks = Utils.splitIntoBlocks(cipher, blocksize);
        for (byte[] block : blocks) {
            BigInteger c = Utils.ByteArrayToBigInt(block);
            BigInteger m = decrypt(c, key);
            byte[] decrypted = Utils.BigIntToByteArray(m);
            byte[] unpacked = unpackMessage(decrypted, blocksize);
            blocks.set(blocks.indexOf(block), unpacked);
        }
        return Utils.concatBlocks(blocks);
    }
    
    public static byte[] generateBlankSignature(byte[] message, RSAKey privkey, RSAKey pubkey) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            BigInteger n = pubkey.getModulus();
            BigInteger e = pubkey.getExponent();
            BigInteger k = BigInteger.probablePrime(n.bitLength()/2, new SecureRandom());
            while (k.gcd(n).compareTo(BigInteger.ONE) != 0) {
                k = k.nextProbablePrime();
            }
            BigInteger ke = k.modPow(e, n);
            BigInteger kinv = k.modInverse(n);
            List<byte[]> blocks = Utils.splitIntoBlocks(md.digest(message), n.bitLength() - 2);
            for (byte[] block : blocks) {
                byte[] packed = packMessage(block, n.bitLength());
                BigInteger m = Utils.ByteArrayToBigInt(packed);
                BigInteger mblanked = m.multiply(ke).mod(n);
                BigInteger mblankedsignature = encrypt(mblanked, privkey);
                BigInteger msignature = mblankedsignature.multiply(kinv).mod(n);
                byte[] signature = Utils.BigIntToByteArray(msignature);
                blocks.set(blocks.indexOf(block), signature);
            }
            return Utils.concatBlocks(blocks);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSAAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static byte[] generateSignature(byte[] message, RSAKey privkey) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messagehash = md.digest(message);
            return encrypt(messagehash, privkey);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSAAlgorithm.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
