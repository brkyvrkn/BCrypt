package utils;

import java.math.BigInteger;

public class Euclid {

    public static BigInteger gcd(BigInteger n1, BigInteger n2) {
        // Number 1 should be greater than number 2
        // To prevent over computing
        if (n1.compareTo(n2) == -1) {
            BigInteger temp = n1;
            n1 = n2;
            n2 = temp;
        }
        while (!n2.equals(BigInteger.ZERO)) {
            BigInteger[] result = n1.divideAndRemainder(n2);
            n1 = n2;
            n2 = result[1];
        }
        return n1;
    }

    public static BigInteger[] extendedGCD(BigInteger a, BigInteger p, BigInteger x, BigInteger y) {
        BigInteger[] extended = new BigInteger[3];
        BigInteger tempP = p;

        while (a.compareTo(BigInteger.ONE) == 1) {
            BigInteger[] result = a.divideAndRemainder(p);
            BigInteger quotient = result[0];
            BigInteger remainder = result[1];

            a = p;
            p = remainder;

            BigInteger tempY = y;
            y = x.subtract(quotient.multiply(y));
            x = tempY;
//			System.out.printf("a=%d, p=%d, x=%d, y=%d\n", a, p, x, y);
        }

        // Residue value of x
        if (x.compareTo(BigInteger.ZERO) == -1)
            x = x.add(tempP);

        extended[0] = x;	// Inverse modulo
        extended[1] = y;
        extended[2] = a;	// GCD
        return extended;
    }

    public static Boolean isCoprime(BigInteger n1, BigInteger n2) {
        return Euclid.gcd(n1, n2).compareTo(BigInteger.ONE) == 0;
    }

    public static BigInteger multiplicativeInverse(BigInteger a, BigInteger p) {
        // Calculate a^-1(modp)
        if (!Euclid.isCoprime(a, p)) {
            System.out.printf("%d and %d are not relatively prime.\n", a, p);
            return BigInteger.ZERO;
        }

        // Initial states
        BigInteger x = BigInteger.ONE;
        BigInteger y = BigInteger.ZERO;
        return Euclid.extendedGCD(a, p, x, y)[0];
    }
}
