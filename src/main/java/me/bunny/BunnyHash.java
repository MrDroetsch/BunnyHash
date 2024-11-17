package me.bunny;

import me.bunny.encoder.Encoder;

import java.math.BigInteger;

public class BunnyHash {

    private final Encoder encoder;
    private final BigInteger capacity;
    private final BigInteger multiplier;
    private final BigInteger offset;
    private final BigInteger modInv;
    private final BigInteger ZERO = new BigInteger("0");
    private final BigInteger ONE = new BigInteger("1");

    public BunnyHash(String multiplierStr, Encoder encoder, String offsetStr) {
        multiplier = new BigInteger(multiplierStr);
        offset = new BigInteger(offsetStr);
        capacity = encoder.getCapacity();
        if(offset.compareTo(capacity) >= 0) {
            throw new ArithmeticException("Offset must be less than capacity");
        }
        if(offset.compareTo(ZERO) < 0) {
            throw new ArithmeticException("Offset can not be negative");
        }
        if(multiplier.compareTo(ONE) <= 0) {
            throw new ArithmeticException("Multiplicator must be greater than 1");
        }
        if(gcd(multiplier, capacity).compareTo(ONE) != 0) {
            throw new ArithmeticException("Multiplicator must be coprime to capacity");
        }

        this.encoder = encoder;
        modInv = multiplier.modInverse(capacity);
    }

    public BunnyHash(String multiplierStr, Encoder encoder) {
        this(multiplierStr, encoder, "0");
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
        BigInteger hashInt = multiplier.multiply(id).add(offset).mod(capacity);
        return encoder.intToBase(hashInt);
    }

    public BigInteger reverse(String hash) {
        if(hash.length() != encoder.getLength()) {
            throw new ArithmeticException("Hash has wrong length");
        }
        return encoder.baseToInt(hash).subtract(offset).add(capacity).multiply(modInv).mod(capacity);
    }

}
