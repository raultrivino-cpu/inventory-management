package com.rtrivino.inventory.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rtrivino.inventory.entity.Customer;
import com.rtrivino.inventory.exception.ElementNotFoundException;
import com.rtrivino.inventory.repository.CustomerRepository;
import com.rtrivino.inventory.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    
    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
       return customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
         return customerRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Cliente no encontrado"));
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer update(Long id, Customer cliente) {
        Customer customerDb = customerRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Cliente no encontrado con id: " + id));

        customerDb.setNombre(cliente.getNombre());
        customerDb.setNombre(cliente.getNombre());
        customerDb.setApellido(cliente.getApellido());
        customerDb.setTelefono(cliente.getTelefono());
        customerDb.setMail(cliente.getMail());
        
        return customerRepository.save(customerDb);
    }

}
