package com.gamelend.servicies;

import com.gamelend.entities.Customer;
import com.gamelend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            customer.setCustomerId(id);
            return Optional.of(customerRepository.save(customer));
        }
        return Optional.empty();
    }

    public Optional<Customer> deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent(customerRepository::delete);
        return customer;
    }


}
