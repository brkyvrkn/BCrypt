package model;

import java.math.BigInteger;
import java.util.Arrays;

public class User {
    // Attributes
    private String name;
    private final UserType type;
    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger[] domainParams;

    public User(String name, UserType type, BigInteger[] domainParams) {
        super();
        setName(name);
        setDomainParams(domainParams);

        this.type = type;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(BigInteger publicKey) {
        this.publicKey = publicKey;
    }

    public UserType getType() {
        return type;
    }

    public BigInteger[] getDomainParams() {
        return domainParams;
    }

    public void setDomainParams(BigInteger[] params) {
        // First element is p
        // Second is g
        assert(params.length == 2);

        this.domainParams = params;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", type=" + type + ", privateKey=" + privateKey + ", publicKey=" + publicKey
                + ", domainParams(p,g)=" + Arrays.toString(domainParams) + "]";
    }
}
