package helper;

import utils.Fermat;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class BigIntegerExtension {

    /**
     * Just limited random
     * @param maxBitLen Limit
     * @return Very big number
     */
    public static BigInteger randomBigInt(int maxBitLen) {
        Random rand = new Random();
        BigInteger randBigInt = new BigInteger(maxBitLen, rand);

        return randBigInt;
    }

    /**
     * Bounded random
     * @param maxBitLen Limit
     * @param lower Base
     * @param upper Peak
     * @return Very big number within specified range
     */
    public static BigInteger randomBigInt(int maxBitLen, BigInteger lower, BigInteger upper) {
        Random rand = new Random();
        BigInteger randBigInt = new BigInteger(maxBitLen, rand);
        BigInteger bound = upper.subtract(lower).abs();

        if (randBigInt.compareTo(lower) == -1)
            randBigInt = randBigInt.add(lower);
        if (randBigInt.compareTo(upper) == 1)
            randBigInt = randBigInt.mod(bound).add(lower);

        return randBigInt;
    }

    /**
     * Bounded and limited random
     * @param upper Peak as String
     * @param lower Base as String
     * @return Very big number
     */
    public static BigInteger randomBigInt(String upper, String lower) {
        BigInteger upperBigInt = new BigInteger(upper);
        BigInteger lowerBigInt = new BigInteger(lower);

        int maxBitLen = upperBigInt.bitLength();

        return BigIntegerExtension.randomBigInt(maxBitLen, lowerBigInt, upperBigInt);
    }

    /**
     * Generate a prime factors of given any number
     * @param number would like to divide
     * @return All prime factors with ascending order
     */
    public static ArrayList<BigInteger> primeFactors(BigInteger number) {
        BigInteger divider = BigInteger.ONE;
        ArrayList<BigInteger> factors = new ArrayList<BigInteger>();

        // Return list if any number cannot be divide the number
        if (Fermat.primalityTest(number, 10))
            return factors;

        boolean willPick = true;
        while ((number.compareTo(BigInteger.ONE) != 0)) {
            // If the current prime number is no longer divisible
            if (willPick) {
                divider = divider.nextProbablePrime();
                willPick = false;
            }

            BigInteger[] result = number.divideAndRemainder(divider);

            if (result[1].compareTo(BigInteger.ZERO) == 0) {
                // Number iterator is divisible by p
                factors.add(divider);
                number = result[0];		// Assign quotient to the number
            } else {
                willPick = true;
            }
        }
        return factors;
    }
}
