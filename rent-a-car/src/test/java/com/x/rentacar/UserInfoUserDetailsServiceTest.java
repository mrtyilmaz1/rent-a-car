package com.x.rentacar;
import com.x.rentacar.enums.Roles;
import com.x.rentacar.model.Customer;
import com.x.rentacar.repository.CustomerRepository;
import com.x.rentacar.service.UserInfoUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserInfoUserDetailsServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private UserInfoUserDetailsService userDetailsService;



    @Test
    void loadUserByUsername() {
        // Arrange
        Customer customer = createTestCustomer();
        String email = "test@example.com";
        customer.setRoles(Roles.ROLE_USER);

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(customer.getEmail(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userDetailsService.loadUserByUsername(email));
        assertEquals("user not found" + email, exception.getMessage());

        // Add more test cases based on your specific requirements
    }

    // Add more tests for different scenarios

    private Customer createTestCustomer() {
        // Create and return a sample Customer object for testing
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setEmail("test@example.com");
        // Set other properties as needed
        return customer;
    }
}