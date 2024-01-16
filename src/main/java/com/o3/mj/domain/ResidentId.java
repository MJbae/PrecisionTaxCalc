package com.o3.mj.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class ResidentId {
    @Transient
    private final Encryptor encryptor = new Encryptor();
    private String encrypted;

    public Encryptor getEncryptor() {
        return encryptor;
    }


    public ResidentId(String original) {
        this.encrypted = encryptor.encrypt(original);
    }

    public String toOriginal() {
        return encryptor.decrypt(this.encrypted);
    }
}
