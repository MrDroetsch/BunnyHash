package me.bunny.encoder;

import java.math.BigInteger;

public class Base26Encoder extends Encoder {

    public Base26Encoder(int length) {
        super(new BigInteger("26"), length);
    }

    @Override
    public BigInteger baseToInt(String baseStr) {
        if(baseStr.length() != length)
            throw new ArithmeticException("Length isn't fitting to decode");
        BigInteger result = new BigInteger("0");
        for (int i = 0; i < length; i++) {
            result = result.multiply(base);
            result = result.add(new BigInteger(String.valueOf((int) (baseStr.charAt(i) - 'A'))));
        }
        return result;
    }

    @Override
    public String intToBase(BigInteger number) {
        StringBuilder result = new StringBuilder();

        while (number.compareTo(new BigInteger("0")) > 0) {
            BigInteger remainder = number.mod(base);
            result.append((char) ('A' + remainder.intValue()));
            number = number.divide(base);
        }
        result.reverse();

        while (result.length() < length) {
            result.insert(0, 'A');
        }

        return result.toString();
    }
}
