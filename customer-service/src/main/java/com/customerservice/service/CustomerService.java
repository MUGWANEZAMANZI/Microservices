package com.customerservice.service;

import com.customerservice.dto.LoginRequest;
import com.customerservice.dto.LoginResponse;
import com.customerservice.dto.UserRegistrationRequest;
import com.customerservice.dto.UserRegistrationResponse;
import com.customerservice.model.Customer;
import com.customerservice.repository.CustomerRepository;
import com.customerservice.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public CustomerService(CustomerRepository repository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public UserRegistrationResponse register(UserRegistrationRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already registered: " + request.email());
        }
        Customer customer = new Customer();
        customer.setFullName(request.fullName());
        customer.setEmail(request.email());
        customer.setPhone(request.phone());
        customer.setPassword(passwordEncoder.encode(request.password()));

        Customer saved = repository.save(customer);
        String token = jwtService.generateToken(saved.getEmail());

        return new UserRegistrationResponse(saved.getId(), saved.getFullName(), saved.getEmail(), token);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        Customer customer = repository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        String token = jwtService.generateToken(customer.getEmail());
        return new LoginResponse(token, customer.getEmail(), customer.getFullName());
    }

    public Customer create(Customer customer) { return repository.save(customer); }
    public List<Customer> findAll() { return repository.findAll(); }
    public Customer findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
