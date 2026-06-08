package com.rtrivino.inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rtrivino.inventory.entity.Customer;
import com.rtrivino.inventory.service.CustomerService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller responsible for customer management operations.
 *
 * <p>
 * Customers are part of the business domain model and can be associated
 * with purchase orders. This controller exposes standard CRUD endpoints to
 * manage customer information.
 * </p>
 *
 * <p>
 * Authorization rules for these endpoints are handled centrally by the
 * Spring Security configuration.
 * </p>
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer category) {
        return customerService.save(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }

    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer cliente) {
        return customerService.update(id, cliente);
    }
}
