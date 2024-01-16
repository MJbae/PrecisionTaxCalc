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

    private String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(password.getBytes());

            return bytesToHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error during Hashing: ", e);
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
