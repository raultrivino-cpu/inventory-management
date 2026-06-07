package com.rtrivino.inventory.service;

import java.util.List;

import com.rtrivino.inventory.entity.Customer;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> findAll();
    Customer findById(Long id);
    void delete(Long id);
    Customer update(Long id, Customer cliente);
}
