package crypto.zad3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class RSAKey {

    private final List<BigInteger> valueList;

    public RSAKey(List<BigInteger> valueList) {
        this.valueList = valueList;
    }
    
    public RSAKey(BigInteger[] values) {
        this.valueList = new ArrayList<>(Arrays.asList(values));
    }
    
    public RSAKey(BigInteger modulus, BigInteger exponent) {
        this.valueList = new ArrayList<>();
        this.valueList.add(modulus);
        this.valueList.add(exponent);
    }

    public BigInteger getModulus() {
        return valueList.get(0);
    }
    
    public BigInteger getExponent() {
        return valueList.get(1);
    }
    
    public List<BigInteger> getValuesList() {
        return valueList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RSAKey) {
            RSAKey other = (RSAKey)obj;
            return this.valueList.equals(other.getValuesList());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.valueList);
        return hash;
    }
}
