package com.o3.mj.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "customer")
public class Customer {
    @Column(unique = true, length = 40)
    private String userId;

    @Column(length = 80)
    private String name;

    @Embedded
    @AttributeOverride(name = "encrypted", column = @Column(name = "residentId", length = 60))
    private ResidentId residentId;

    @Embedded
    @AttributeOverride(name = "hashed", column = @Column(name = "password", length = 64))
    private Password password;

    private final LocalDateTime registeredAt = LocalDateTime.now();

    @Id
    @TableGenerator(name = "userIdGenerator", table = "sequence", allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "userIdGenerator")
    private Long id;

    public Customer() {
    }

    public Customer(String userId, String name, String residentId, String password) {
        this.userId = userId;
        this.name = name;
        this.residentId = new ResidentId(residentId);
        this.password = new Password(password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name=" + name +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return Objects.equals(userId, customer.userId);
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}
