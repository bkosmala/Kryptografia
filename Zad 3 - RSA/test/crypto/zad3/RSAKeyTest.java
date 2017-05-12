package crypto.zad3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RSAKeyTest {
    
    private static BigInteger modulus;
    private static BigInteger exponent;
    
    @BeforeClass
    public static void setUpClass() {
        modulus = new BigInteger("1234567891011121314151617181920", 16);
        exponent = new BigInteger("7181920", 16);
    }

    @Test
    public void testGetModulus() {
        System.out.println("getModulus");
        RSAKey instance = new RSAKey(modulus, exponent);
        BigInteger expResult = modulus;
        BigInteger result = instance.getModulus();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetExponent() {
        System.out.println("getExponent");
        RSAKey instance = new RSAKey(modulus, exponent);
        BigInteger expResult = exponent;
        BigInteger result = instance.getExponent();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetValuesList() {
        System.out.println("getValuesList");
        RSAKey instance = new RSAKey(modulus, exponent);
        List<BigInteger> expResult = new ArrayList<>();
        expResult.add(modulus);
        expResult.add(exponent);
        List<BigInteger> result = instance.getValuesList();
        assertEquals(expResult, result);
    }
    
}
