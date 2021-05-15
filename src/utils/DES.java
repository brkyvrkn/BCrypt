package utils;

import java.math.BigInteger;

public class DES {

    private BigInteger[][] initialPermutationTable;
    private BigInteger[][] finalPermutationTable;
    private BigInteger[][] expansionPermutationTable;

    private BigInteger[][][] sBoxes;
    private BigInteger[][] pBoxTable;

    // Key generation
    private BigInteger[] rightShiftTable;
    private BigInteger[] leftShiftTable;
    private BigInteger[][] compressionPBox;

    public DES() {

    }

    public void test1() {
        String plainText = "123456ABCD123456";
        String key = "AABB09182736CCDD";
        String expectedCipher = "C0B7A8D05F3A829C";
    }

    public void test2() {
        String plainText = "02468aceeca86420";
        String key = "0f1571c947d9e859";
        String expectedCipher = "da02ce3a89ecac3b";
    }
}
