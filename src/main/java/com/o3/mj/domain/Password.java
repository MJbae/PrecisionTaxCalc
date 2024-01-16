package com.o3.mj.domain;

import jakarta.persistence.Embeddable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Embeddable
public class Password {
    private static final String HASH_ALGORITHM = "SHA-256";
    private String hashed;

    public Password() {
    }

    public Password(String original) {
        this.hashed = hash(original);
    }

    public boolean isMatched(String target) {
        String hashedTarget = hash(target);
        return hashedTarget.equals(this.hashed);
    }

    public String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(password.getBytes());
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found: " + HASH_ALGORITHM, e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        String hexString = formatter.toString();
        formatter.close();
        return hexString;
    }
}
