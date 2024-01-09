package com.x.rentacar.service;

import com.x.rentacar.enums.Roles;
import com.x.rentacar.model.Customer;
import com.x.rentacar.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    public Customer addCustomer(Customer customer){
        // Rol null ise otomatik olar user tanımlamak için.
        if (Objects.isNull(customer.getRoles())){
            customer.setRoles(Roles.ROLE_USER);
        }
        // şifreyi kodlayarak saklamak için.
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }




}
