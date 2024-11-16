package me.bunny.encoder;

import java.math.BigInteger;

public abstract class Encoder {

    protected BigInteger base;
    protected int length;

    public Encoder(BigInteger base, int length) {
        if(base.compareTo(new BigInteger("1")) < 0) throw new ArithmeticException("Base must be positive");
        this.base = base;
        if(length < 1) throw new ArithmeticException("Length must be positive");
        this.length = length;
    }

    public BigInteger getBase() {
        return base;
    }

    public int getLength() {
        return length;
    }

    public BigInteger getCapacity() {
        return base.pow(length);
    }

    public abstract BigInteger baseToInt(String base);
    public abstract String intToBase(BigInteger number);
}
