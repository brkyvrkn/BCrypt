package com.main;

import model.User;
import model.UserType;
import utils.*;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        firstStep();
        System.out.println("\n=====");
        try {
			secondStep();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }

    public static void firstStep() {
        // Euclid's algorithm test
        BigInteger number1, number2;
        number1 = BigInteger.valueOf(8);
        number2 = BigInteger.valueOf(11);

        BigInteger gcd = Euclid.gcd(number1, number2);
        BigInteger inv = Euclid.multiplicativeInverse(number1, number2);

        System.out.printf("GCD(%d, %d) = %d\n", number1, number2, gcd);
        System.out.printf("Inverse modulo of %d is %d\n", number1, inv);
        System.out.println();

        // Fermat's Theorem test
        String numberInText = "495695033";
        int iterationNumber = 456;
        BigInteger veryBigPrime = new BigInteger(numberInText);
        BigInteger veryBigNotPrime = veryBigPrime.add(BigInteger.ONE); // Make it even
        boolean isPrime = Fermat.primalityTest(veryBigPrime, iterationNumber);
        System.out.printf("%s primality in %d iteration is %b\n", numberInText, iterationNumber, isPrime);
        isPrime = Fermat.primalityTest(veryBigNotPrime, iterationNumber); // Re-test
        System.out.printf("%d primality in %d iteration is %b\n", veryBigNotPrime, iterationNumber, isPrime);
        System.out.println();

        // Fast Modular Exponentiation Test
        // 7^256(mod13) should give 9
        BigInteger A = new BigInteger("7");
        BigInteger B = new BigInteger("256");
        BigInteger C = new BigInteger("13");

        BigInteger mod = FastModExp.modExponentiation(A, B, C);
        System.out.printf("%d^%d(mod%d) = %d\n", A, B, C, mod);
        System.out.println();

        // AES Encryption and Decryption
        String plainText = "Hello World";
        AES algo = new AES("Bar12345Bar12345", "RandomInitVector");

        String encrypted = algo.encrypt(plainText);
        String decrypted = algo.decrypted(encrypted);

        System.out.printf("Original message: %s\n" + "Encrypted: %s\n" + "Decrypted: %s", plainText, encrypted,
                decrypted);
    }

    public static void secondStep() throws UnsupportedEncodingException {
        // Generate p, its length is 8-bit
        BigInteger p = ElGamal.generateP(8);
        BigInteger g = ElGamal.generateG(p);
        BigInteger[] params = {p, g};

        User alice =  new User("Alice", UserType.SENDER, params);
        User bob =  new User("Bob", UserType.RECEIVER, params);
        CA certificate = new CA("BOB-ALICE_CERTIFICATE", params);

        BigInteger[] aliceKeyPairs = ElGamal.generateKeyPairs(p, g);
        BigInteger[] bobKeyPairs = ElGamal.generateKeyPairs(p, g);
        BigInteger[] certificateKeyPairs = ElGamal.generateKeyPairs(p, g);

        //Set the key pairs of instances
        alice.setPublicKey(aliceKeyPairs[0]);
        alice.setPrivateKey(aliceKeyPairs[1]);

        bob.setPublicKey(bobKeyPairs[0]);
        bob.setPrivateKey(bobKeyPairs[1]);

        certificate.setPublicKey(certificateKeyPairs[0]);
        certificate.setPrivateKey(certificateKeyPairs[1]);

        System.out.println(alice);
        System.out.println(bob);
        System.out.println(certificate);
//
        DigitalSignature ds = new DigitalSignature(params);
        String senderMessage = "Hello, I am Bob";
        String receiverMessage = "Hello, I am Alice";
        BigInteger[] senderSignature = ds.sign(alice, senderMessage);
        BigInteger[] receiverSignature = ds.sign(bob, receiverMessage);

        BigInteger hashedSenderMessage = new BigInteger(DigitalSignature.hashWithSHA(senderMessage).getBytes(Config.CHARSET_FORMAT));
        boolean validSender = ds.isValid(senderSignature, alice, hashedSenderMessage);

        BigInteger hashedReceiverMessage = new BigInteger(DigitalSignature.hashWithSHA(receiverMessage).getBytes(Config.CHARSET_FORMAT));
        boolean validReceiver = ds.isValid(receiverSignature, bob, hashedReceiverMessage);

        System.out.printf("Signature is valid = %b for %s\n", validSender, alice.getName());
        System.out.printf("Signature is valid = %b for %s\n", validReceiver, bob.getName());

		String aliceCertFilename = certificate.requestAuthority(alice.getName(), alice.getPublicKey());
		String bobCertFilename = certificate.requestAuthority(bob.getName(), bob.getPublicKey());

		System.out.printf("Certificates exported to [Alice = %s, Bob = %s]\n", aliceCertFilename, bobCertFilename);
    }
}
