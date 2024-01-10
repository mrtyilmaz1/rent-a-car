package com.x.rentacar.controller;

import com.x.rentacar.dto.AuthRequest;
import com.x.rentacar.dto.LoginDto;
import com.x.rentacar.model.Customer;
import com.x.rentacar.service.CustomerService;
import com.x.rentacar.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerControler {

    private final CustomerService customerService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signUp")
    public ResponseEntity<Customer> signUp(@RequestBody Customer customer /* Bu, HTTP isteği içindeki JSON verisini kullanarak bir Customer nesnesi oluşturulmasını sağlar. */){
        return new ResponseEntity<>(customerService.signUp(customer), HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Customer> addUser(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.OK);
    }

    // AuthenticationManager'ı kullanılarak kullanıcının kimlik bilgilerinin doğrulanması gerçekleştirilir.
    @PostMapping("/getToken")
    public ResponseEntity<LoginDto> authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated() /* Eğer kimlik doğrulama başarılıysa, yani kullanıcı geçerliyse, bu blok çalışır. */){
            return new ResponseEntity<>(jwtService.generateToken(authentication), // Bu metodun çağrılmasıyla, geçerli kullanıcının kimlik bilgileri kullanılarak bir JWT (JSON Web Token) oluşturulur.
                    HttpStatus.OK);
        }
        throw new UsernameNotFoundException("invalid user details"); //Eğer kimlik doğrulama başarısız olursa, yetersiz veya hatalı kullanıcı bilgileri nedeniyle bir UsernameNotFoundException fırlatılır.
    }

}
