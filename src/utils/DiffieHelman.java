package utils;

import helper.BigIntegerExtension;

import java.math.BigInteger;
import java.security.KeyException;

public class DiffieHelman {

    // A is sender's private
    // B is receiver's private
	public static BigInteger[] keyExchange(BigInteger a, BigInteger b, BigInteger p, BigInteger g) throws KeyException {
		BigInteger x = BigIntegerExtension.randomBigInt(p.subtract(BigInteger.ONE).toString(), "1");
		BigInteger y = FastModExp.modExponentiation(g, x, p);

		BigInteger senderPublic = FastModExp.modExponentiation(g, a, p);
		BigInteger receiverPublic = FastModExp.modExponentiation(g, b, p);

		System.out.printf("p: %d\tg: %d\t x: %d\ty: %d", p, g, x, y);

		BigInteger sharedSender = FastModExp.modExponentiation(senderPublic, b, p);
		BigInteger sharedReceiver = FastModExp.modExponentiation(receiverPublic, a, p);
		if (sharedSender.compareTo(sharedReceiver) == 0) {
			// X is Private key
			// Y is Public key
			BigInteger[] pairs = {x, y, sharedSender};
			return pairs;
		} else {
			throw new KeyException("Shared key security error");
		}

	}
}
