package me.bunny;

import me.bunny.encoder.Encoder;

import java.math.BigInteger;

public class BunnyHash {

    private Encoder encoder;
    private BigInteger capacity;
    private BigInteger multiplicator;
    private BigInteger offset;
    private BigInteger modInv;
    private final BigInteger ZERO = new BigInteger("0");
    private final BigInteger ONE = new BigInteger("1");

    public BunnyHash(String multiplicatorStr, Encoder encoder, String offsetStr) {
        multiplicator = new BigInteger(multiplicatorStr);
        offset = new BigInteger(offsetStr);
        capacity = encoder.getCapacity();
        if(offset.compareTo(capacity) >= 0) {
            throw new ArithmeticException("Offset must be less than capacity");
        }
        if(offset.compareTo(ZERO) < 0) {
            throw new ArithmeticException("Offset can not be negative");
        }
        if(multiplicator.compareTo(ONE) <= 0) {
            throw new ArithmeticException("Multiplicator must be greater than 1");
        }
        if(gcd(multiplicator, capacity).compareTo(ONE) != 0) {
            throw new ArithmeticException("Multiplicator must be coprime to capacity");
        }

        this.encoder = encoder;
        modInv = multiplicator.modInverse(capacity);
    }

    public BunnyHash(String multiplicatorStr, Encoder encoder) {
        this(multiplicatorStr, encoder, "0");
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        while(b.compareTo(ZERO) > 0) {
            BigInteger tmp = b;
            b = a.mod(b);
            a = tmp;
        }
        return a;
    }

    public String hash(String idStr) {
        BigInteger id = new BigInteger(idStr);
        if(id.compareTo(ZERO) < 0) {
            throw new ArithmeticException("ID can not be negative");
        }
        if(id.compareTo(capacity) >= 0) {
            throw new ArithmeticException("ID is too big for capacity");
        }
        BigInteger hashInt = multiplicator.multiply(id).add(offset).mod(capacity);
        return encoder.intToBase(hashInt);
    }

    public BigInteger reverse(String hash) {
        if(hash.length() != encoder.getLength()) {
            throw new ArithmeticException("Hash has wrong length");
        }
        return encoder.baseToInt(hash).subtract(offset).add(capacity).multiply(modInv).mod(capacity);
    }

}
