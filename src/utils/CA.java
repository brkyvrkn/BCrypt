package utils;

import helper.FileOperator;

import java.math.BigInteger;
import java.util.Arrays;

public class CA {

    // Attributes
    private String name;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private BigInteger[] domainParams;

    public CA() {}

    public CA(String name, BigInteger[] domainParams) {
        super();

        setName(name);
        setDomainParams(domainParams);
    }

    /**
     * @param subjectName: Name of the User
     * @param y: Public key of User
     * @return Certificate filename
     */
    public String requestAuthority(String subjectName, BigInteger y) {
        StringBuilder auth = new StringBuilder();
        String filename = String.format("Certificate_%s.txt", subjectName);

        // Name of the CA
        auth.append(String.format("%s ||", this.name));

        // Name of the user
        auth.append(String.format(" %s ||", subjectName));

        // Public key of the user
        auth.append(String.format(" %s ||", y.toString()));

        // Domain parameters are the same with CA
        // So, we just put the CA's domain parameters
        BigInteger pCA = this.domainParams[0];
        BigInteger gCA = this.domainParams[1];
        auth.append(String.format(" %s %s ||", pCA, gCA));

        // sign so far
        // then append signature
        // write into the file

        // CA's public key at the end of the certificate
        auth.append(String.format(" %s", this.publicKey.toString()));

        FileOperator.writeFile(auth.toString(), filename);
        return filename;
    }


    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(BigInteger publicKey) {
        this.publicKey = publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public BigInteger[] getDomainParams() {
        return domainParams;
    }

    public void setDomainParams(BigInteger[] domainParams) {
        this.domainParams = domainParams;
    }

    @Override
    public String toString() {
        return "CA [name=" + name + ", publicKey=" + publicKey + ", privateKey=" + privateKey + ", domainParams(p,g)="
                + Arrays.toString(domainParams) + "]";
    }
}
