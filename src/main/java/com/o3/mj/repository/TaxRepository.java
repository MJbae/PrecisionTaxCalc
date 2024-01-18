package com.o3.mj.repository;

import com.o3.mj.domain.customer.Customer;
import com.o3.mj.domain.tax.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TaxRepository extends JpaRepository<Tax, Long> {
    Optional<Tax> findByCustomer(Customer customer);
}
