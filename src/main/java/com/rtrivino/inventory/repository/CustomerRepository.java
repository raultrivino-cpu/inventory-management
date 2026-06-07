package com.rtrivino.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rtrivino.inventory.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
