package utils;

import com.main.Config;
import helper.BigIntegerExtension;
import model.User;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigitalSignature {

    // Attributes
    private BigInteger[] domainParams;

    // Constructors
    public DigitalSignature() {}

    public DigitalSignature(BigInteger[] domainParams) {
        setDomainParams(domainParams);
    }

    // Class methods
    public BigInteger[] sign(User usr, String mesg) throws UnsupportedEncodingException {
        // Array like tuple (S1, S2)
        BigInteger[] signature = new BigInteger[2];

        BigInteger pMinusOne = getP().subtract(BigInteger.ONE);
        BigInteger privateKey = usr.getPrivateKey();
        BigInteger secretK = null;

        // Choose a random secret key from p
        while (secretK == null) {
            secretK = BigIntegerExtension.randomBigInt(pMinusOne.toString(), BigInteger.ZERO.toString());
            if (Euclid.gcd(secretK, pMinusOne).compareTo(BigInteger.ONE) == 0)
                break;
            else
                secretK = null;
        }
        // After while scope, secret K is generated.
        // G^K(modp)
        BigInteger r = FastModExp.modExponentiation(getG(), secretK, getP());
        BigInteger hashedMessage = new BigInteger(DigitalSignature.hashWithSHA(mesg).getBytes(Config.CHARSET_FORMAT));

        BigInteger invK = Euclid.multiplicativeInverse(secretK, pMinusOne);

        // K^-1 (m- (x*r))(mod p-1)
        // where m is the hashed message with SHA
        BigInteger s = invK.multiply(hashedMessage.subtract(privateKey.multiply(r))).mod(pMinusOne);

        signature[0] = r;
        signature[1] = s;

        return signature;
    }

    public boolean isValid(BigInteger[] params, User usr, BigInteger hashedMessage) {
        // V1 = G^m (modp)
        BigInteger v1 = FastModExp.modExponentiation(getG(), hashedMessage, getP());

        // V2 = (public^r) * (r^s) (modp)
        BigInteger r = params[0];
        BigInteger s = params[1];
        BigInteger y = usr.getPublicKey();

        // Combine the formula as (public*r)^(r*s) (modp)
        BigInteger A = y.multiply(r);
        BigInteger B = r.multiply(s);
        BigInteger v2 = FastModExp.modExponentiation(A, B, getP());

        // Signature is valid iff
        // V1 = V2
        return v1.compareTo(v2) == 0;
    }

    public static String hashWithSHA(String input) {
        try {
            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance(Config.HASH_ALGO);

            // digest() method called
            // to calculate message digest of an input
            // and return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            // For specifying wrong message digest algorithms
            System.out.println("Exception thrown" + " for incorrect algorithm: " + e);
            return null;
        }
    }


    // Getters & Setters
    public BigInteger[] getDomainParams() {
        return domainParams;
    }

    public void setDomainParams(BigInteger[] domainParams) {
        this.domainParams = domainParams;
    }

    public BigInteger getP() {
        return this.domainParams[0];
    }

    public void setP(BigInteger p) {
        this.domainParams[0] = p;
    }

    public BigInteger getG() {
        return this.domainParams[1];
    }

    public void setG(BigInteger g) {
        this.domainParams[1] = g;
    }

    @Override
    public String toString() {
        return "DigitalSignature [domainParams=" + domainParams + "]";
    }
}
