package com.o3.mj.domain;


import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "customer")
@NoArgsConstructor
public class Customer {
    @Id
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "id", length = 40))
    private CustomerId id;

    @Column(length = 10)
    private String name;

    @Embedded
    @AttributeOverride(name = "encrypted", column = @Column(name = "residentId", length = 60))
    private ResidentId residentId;

    @Embedded
    @AttributeOverride(name = "hashed", column = @Column(name = "password", length = 64))
    private Password password;

    private final LocalDateTime registeredAt = LocalDateTime.now();

    public Customer(CustomerId customerId, String name, ResidentId residentId, Password password) {
        this.id = customerId;
        this.name = name;
        this.residentId = residentId;
        this.password = password;
    }

    public CustomerId getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getOriginResidentId(){
        return this.residentId.toOriginal();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + id +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
