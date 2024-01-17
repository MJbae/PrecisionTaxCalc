package com.o3.mj.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Password {
    @Transient
    private final Hashier hashier = new Hashier();
    private String hashed;

    public Password(String original) {
        this.hashed = hashier.hash(original);
    }

    public boolean isMatched(String target) {
        String hashedTarget = hashier.hash(target);
        return hashedTarget.equals(this.hashed);
    }
}
