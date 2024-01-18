package com.o3.mj.repository;

import com.o3.mj.domain.customer.Customer;
import com.o3.mj.domain.customer.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, CustomerId> { }
