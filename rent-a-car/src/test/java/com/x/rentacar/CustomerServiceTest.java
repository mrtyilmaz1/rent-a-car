package com.x.rentacar;

import com.x.rentacar.model.Customer;
import com.x.rentacar.repository.CustomerRepository;
import com.x.rentacar.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void addCustomer() {
        // Arrange
        Customer customer = createTestCustomer();
        when(customerRepository.existsByEmail(customer.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(customer.getPassword())).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Customer result = customerService.addCustomer(customer);

        // Assert
        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    void signup() {
        // Arrange
        Customer customer = createTestCustomer();
        when(customerRepository.existsByEmail(customer.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(customer.getPassword())).thenReturn("encodedPassword");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Customer result = customerService.signup(customer);

        // Assert
        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
    }


    private Customer createTestCustomer() {
        // Create and return a sample Customer object for testing
        Customer customer = new Customer();
        customer.setId(1L);
        // Set other properties as needed
        return customer;
    }
}