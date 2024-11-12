package me.bunny;

import java.math.BigInteger;

public class InvertablePermutation {

    public static String bunnyHash(int id, int anzahl_zeichen, int offset) {
        BigInteger n = new BigInteger("26").pow(anzahl_zeichen);
        BigInteger x = new BigInteger(String.valueOf(id));
        if(x.compareTo(n) == 0 || x.compareTo(n) > 0) {
            throw new ArithmeticException("ID ist zu groß für die Anzahl von Zeichen");
        }
        BigInteger a = new BigInteger("57885161");          // Teilerfremd zu n, da primzahl
        BigInteger c = new BigInteger(String.valueOf(offset));  // mögliches Offset (seed)
        BigInteger hashInt = a.multiply(x).add(c).mod(n);       // (a * (x + c)) % n
        int hashValue = hashInt.intValue();
        String hash = intToBase26(hashValue, anzahl_zeichen);
        return hash;
    }

    public static int bunnyReverse(String hash, int offset) {
        BigInteger n = new BigInteger("26").pow(hash.length());
        BigInteger x = new BigInteger(String.valueOf(base26ToInt(hash)));
        BigInteger a = new BigInteger("57885161");                  // Teilerfremd zu n, da primzahl
        BigInteger c = new BigInteger(String.valueOf(offset));          // mögliches Offset (seed)
        BigInteger modInv = a.modInverse(n);
        BigInteger id = x.subtract(c).add(n).multiply(modInv).mod(n);   // ((Inv * x)-c+n) % n
        return id.intValue();
    }

    public static String intToBase26(int number, int length) {
        StringBuilder result = new StringBuilder();

        while (number > 0) {
            int remainder = number % 26;
            result.append((char) ('A' + remainder));
            number = (number / 26);
        }
        result.reverse();

        while (result.length() < length) {
            result.insert(0, 'A');
        }

        return result.toString();
    }

    public static int base26ToInt(String base26) {
        int result = 0;
        for (int i = 0; i < base26.length(); i++) {
            result *= 26;
            result += base26.charAt(i) - 'A';
        }
        return result;
    }

    public static void main(String[] args) {
        for(int i = 3_000_000; i < 3_000_101; i++) {
            String hash0 = bunnyHash(i, 5, 0);
            int id0 = bunnyReverse(hash0, 0);
            String hash1 = bunnyHash(i, 5, 826359);
            int id1 = bunnyReverse(hash1, 826359);
            System.out.println(i + ": " + hash0 + ", Invers: " + id0 + " || " + hash1 + ", Invers: " + id1);
        }
    }
}
