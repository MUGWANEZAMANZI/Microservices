package com.customerservice.service;

import com.customerservice.model.Customer;
import com.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(Customer customer) { return repository.save(customer); }
    public List<Customer> findAll() { return repository.findAll(); }
    public Customer findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
