package com.x.rentacar.controller;

import com.x.rentacar.dto.AuthRequest;
import com.x.rentacar.dto.LoginDto;
import com.x.rentacar.model.Customer;
import com.x.rentacar.service.AuthenticationService;
import com.x.rentacar.service.CustomerService;
import com.x.rentacar.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerControler {

    private final CustomerService customerService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<Customer> signUp(@RequestBody Customer customer /* Bu, HTTP isteği içindeki JSON verisini kullanarak bir Customer nesnesi oluşturulmasını sağlar. */){
        return new ResponseEntity<>(customerService.signUp(customer), HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Customer> addUser(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.OK);
    }

    @PostMapping("/getToken")
    public ResponseEntity<LoginDto> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return authenticationService.authenticateAndGetToken(authRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        return authenticationService.logout(request, response);
    }



}
