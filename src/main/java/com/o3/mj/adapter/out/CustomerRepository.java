package com.o3.mj.adapter.out;

import com.o3.mj.domain.Customer;
import com.o3.mj.domain.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, CustomerId> { }
