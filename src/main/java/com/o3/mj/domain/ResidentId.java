package com.o3.mj.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResidentId that = (ResidentId) o;

        return Objects.equals(encrypted, that.encrypted);
    }

    @Override
    public int hashCode() {
        return encrypted != null ? encrypted.hashCode() : 0;
    }
}
