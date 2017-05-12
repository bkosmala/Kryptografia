package crypto.zad3;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class RSAKeysGenerator {

    RSAKey publicKey;
    RSAKey privateKey;

    private static BigInteger gcd(BigInteger a, BigInteger b) {
        while (b.compareTo(BigInteger.ZERO) != 0) {
            BigInteger c = a.mod(b);
            a = b;
            b = c;
        }
        return a;
    }

    public RSAKeysGenerator() {
        this.privateKey = null;
        this.publicKey = null;
    }

    public RSAKey getPrivateKey() {
        return this.privateKey;
    }

    public RSAKey getPublicKey() {
        return this.publicKey;
    }

    public void generateKeys(int keySize) {
        BigInteger p, q, n, e, d, eulerfunc;
        Random rg = new SecureRandom();

        do {
            do {
                do {
                    int psize = keySize / 2 + (int) (-10 + rg.nextDouble() * 20);
                    p = BigInteger.probablePrime(psize, rg);
                } while (p.compareTo(BigInteger.ZERO) <= 0);
                do {
                    int qsize = keySize / 2 + (int) (-10 + rg.nextDouble() * 20);
                    q = BigInteger.probablePrime(qsize, rg);
                } while (p.compareTo(BigInteger.ZERO) <= 0);
            } while ((p.xor(q)).bitCount() < 60); // hamming distance - xor will add zeros to the shorter value
            n = p.multiply(q);
        } while (n.bitLength() != keySize);
        eulerfunc = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // (p-1)(q - 1)
        do {
            e = new BigInteger(32, rg);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(eulerfunc) >= 0 || gcd(e, eulerfunc).compareTo(BigInteger.ONE) != 0);
        d = e.modInverse(eulerfunc);
        publicKey = new RSAKey(new BigInteger[]{n, e});
        privateKey = new RSAKey(new BigInteger[]{n, d});
    }
}
