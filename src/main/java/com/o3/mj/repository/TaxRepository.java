package com.o3.mj.repository;

import com.o3.mj.domain.Customer;
import com.o3.mj.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaxRepository extends JpaRepository<Tax, Long> {
    Optional<Tax> findByCustomer(Customer customer);
}
