package com.x.rentacar.service;

import com.x.rentacar.config.JwtUserDetails;
import com.x.rentacar.model.Customer;
import com.x.rentacar.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Customer> userInfo = customerRepository.findByEmail(email);
        return userInfo.map(JwtUserDetails::new)
                .orElseThrow(() -> new RuntimeException("user not found" + email));
    }
}

