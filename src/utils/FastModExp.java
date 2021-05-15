package utils;

import java.math.BigInteger;

public class FastModExp {

    private static BigInteger[] orderedExpMods(BigInteger A, int maxB, BigInteger C) {
        // Helper method just for fast Modular Exponentiation Algorithm
        BigInteger[] mods = new BigInteger[maxB + 1];
        for (int i = 0; i <= maxB; i++) {
            // Calculate A^exp where exp=2^i
            BigInteger exp = BigInteger.TWO.pow(i);

            if (exp.compareTo(BigInteger.ONE) == 0)
                mods[0] = A.modPow(exp, C);
            else if (i > 0) {
                // After first iteration
                // Doubled exponent
                // Calculate A^exp(modC) by ((A^(exp/2)*(A^(exp/2))(modC)
                // A^(exp/2) indicates to the value of mods[i-1]
                BigInteger prev = mods[i-1];
                mods[i] = prev.pow(2).mod(C);
            }
        }
        return mods;
    }

    public static BigInteger modExponentiation(BigInteger A, BigInteger B, BigInteger C) {
        // Calculation of A^B(modC)
        String binary = B.toString(2);
        BigInteger[] twos = new BigInteger[binary.length()];

        for (int i = binary.length() - 1; i >= 0; i--) {
            int digit = binary.length() - i - 1;
            twos[digit] = BigInteger.ZERO;

            if (binary.charAt(i) == '1') {
                BigInteger exp = BigInteger.TWO.pow(digit);
                twos[digit] = exp;
            }
        }

        BigInteger[] temp = FastModExp.orderedExpMods(A, binary.length() - 1, C);
        BigInteger totalMult = BigInteger.ONE;

        for (int j = 0; j < binary.length(); j++) {
            if (twos[j].compareTo(BigInteger.ZERO) != 0) {
                totalMult = totalMult.multiply(temp[j]);
            }
        }
        return totalMult.mod(C);
    }
}
