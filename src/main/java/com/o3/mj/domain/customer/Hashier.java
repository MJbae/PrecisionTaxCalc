package com.o3.mj.domain.customer;

import java.security.MessageDigest;
import java.util.Formatter;

public class Hashier {
    private static final String HASH_ALGORITHM = "SHA-256";

    public String hash(String originData) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(originData.getBytes());

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
