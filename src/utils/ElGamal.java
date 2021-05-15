package utils;

import helper.BigIntegerExtension;

import java.math.BigInteger;
import java.util.ArrayList;

public class ElGamal {

    /**
     * Static P-Generator
     * @return 11-bit length p
     */
    public static BigInteger generateP() {
        String elevenBitNum = "9999";
        BigInteger probablePrime = null;
        int iteration = 5;
        do {
            probablePrime = BigIntegerExtension.randomBigInt(elevenBitNum, "2");
            if (!Fermat.primalityTest(probablePrime, iteration))
                probablePrime = null;
        } while(probablePrime == null);
        return probablePrime;
    }

    // Helper
    public static BigInteger generateP(int bitLen) {
        BigInteger probablePrime = null;
        int iteration = 5;
        do {
            probablePrime = BigIntegerExtension.randomBigInt(bitLen);
            if (!Fermat.primalityTest(probablePrime, iteration))
                probablePrime = null;
        } while(probablePrime == null);
        return probablePrime;
    }

    public static BigInteger generateG(BigInteger p) {
        BigInteger pMinusOne = p.subtract(BigInteger.ONE);
        ArrayList<BigInteger> factors = BigIntegerExtension.primeFactors(pMinusOne);
        // Base
        BigInteger prev = factors.get(0);

        if (Euclid.gcd(prev, pMinusOne).compareTo(BigInteger.ONE) == 0)
            return prev;

        for (int i = 1; i < factors.size(); i++) {
            BigInteger current = factors.get(i);
            if (prev.equals(current))
                continue;

            if (Euclid.gcd(current, pMinusOne).compareTo(BigInteger.ONE) == 0)
                return current;
            prev = current;
        }

        // If did not find in the prime factors
        BigInteger base = BigInteger.TWO;
        while(true) {
            if (Euclid.gcd(base, pMinusOne).compareTo(BigInteger.ONE) == 0)
                break;
            else
                base = base.add(BigInteger.ONE);
        }
        return base;
//		return BigInteger.ZERO;
    }

    /**
     * Key-Pair generation
     * @param p public
     * @param g private
     * @return
     */
    public static BigInteger[] generateKeyPairs(BigInteger p, BigInteger g) {
        BigInteger x = BigIntegerExtension.randomBigInt(p.subtract(BigInteger.ONE).toString(), "1");
        BigInteger y = FastModExp.modExponentiation(g, x, p);
        BigInteger[] pairs = {y, x};
        return pairs;
    }
}
