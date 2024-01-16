package com.o3.mj.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class CustomerId {
    private String id;

    public CustomerId(String id){
        this.id = id;
    }

}
