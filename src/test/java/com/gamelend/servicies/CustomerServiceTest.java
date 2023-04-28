package com.gamelend.servicies;

import com.gamelend.entities.Customer;
import com.gamelend.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer(null, "John Doe", 30, "123 Main St", "john.doe@example.com", "555-1234");
        Customer savedCustomer = new Customer(1L, "John Doe", 30, "123 Main St", "john.doe@example.com", "555-1234");
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        Customer result = customerService.createCustomer(customer);

        verify(customerRepository, times(1)).save(customer);
        assertEquals(savedCustomer, result);
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer(1L, "John Doe", 30, "123 Main St", "john.doe@example.com", "555-1234");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.getCustomerById(1L);

        verify(customerRepository, times(1)).findById(1L);
        assertEquals(Optional.of(customer), result);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "John Doe", 30, "123 Main St", "john.doe@example.com", "555-1234"));
        customers.add(new Customer(2L, "Jane Smith", 25, "456 Elm St", "jane.smith@example.com", "555-5678"));
        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        verify(customerRepository, times(1)).findAll();
        assertEquals(customers, result);
    }

    @Test
    void testUpdateCustomer() {
        Customer existingCustomer = new Customer(1L, "John Doe", 30, "123 Main St", "john.doe@example.com", "555-1234");
        Customer updatedCustomer = new Customer(1L, "John Doe Jr", 25, "123 Main St", "john.doe@example.com", "555-1234");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        Optional<Customer> result = customerService.updateCustomer(1L, updatedCustomer);

        assertTrue(result.isPresent());
        assertEquals("John Doe Jr", result.get().getName());
        assertEquals(25, result.get().getAge());
    }
}
