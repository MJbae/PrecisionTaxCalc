package com.o3.mj.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class ResidentId {
    private static final String ENCRYPTION_ALGORITHM = "AES";
    private static final SecretKey secretKey = generateKey();
    private String encrypted;

    public ResidentId(String original) {
        this.encrypted = encrypt(original);
    }

    public String toOriginal() {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(this.encrypted));

            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error during decryption", e);
        }
    }

    private String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error during encryption", e);
        }
    }

    private static SecretKey generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
            keyGenerator.init(128);

            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error generating AES key", e);
        }
    }
}
