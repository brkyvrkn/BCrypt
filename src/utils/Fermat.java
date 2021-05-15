package utils;

import helper.BigIntegerExtension;

import java.math.BigInteger;

public class Fermat {

    public static boolean primalityTest(BigInteger number, int iterNum) {
        assert(number.compareTo(BigInteger.ZERO) != 0);
        assert(number.compareTo(BigInteger.ONE) != 0);
        assert(number.compareTo(BigInteger.TWO) != 0);

        boolean result = true;
        String upperBound = number.subtract(BigInteger.TWO).toString();
        int iterCounter = 0;

        while (iterCounter < iterNum) {
            BigInteger randInt = BigIntegerExtension.randomBigInt(upperBound, "2");
            if (!Euclid.isCoprime(randInt, number))
                continue;

            BigInteger[] extEuc = Euclid.extendedGCD(number, randInt, BigInteger.ONE, BigInteger.ZERO);
            if (extEuc[2].compareTo(BigInteger.ONE) != 0)
                continue;
            else {
                // Test a^(r-1)=1(modr)
                BigInteger congurent = randInt.modPow(number.subtract(BigInteger.ONE), number);
                if (congurent.compareTo(BigInteger.ONE) != 0)
                    return false;
            }
            iterCounter += 1;
        }
        return result;
    }
}
