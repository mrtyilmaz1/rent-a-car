package com.x.rentacar.service;

import com.x.rentacar.dto.AuthRequest;
import com.x.rentacar.dto.LoginDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ResponseEntity<LoginDto> authenticateAndGetToken(AuthRequest authRequest) {
        // AuthenticationManager'ı kullanılarak kullanıcının kimlik bilgilerinin doğrulanması gerçekleştirilir.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) { // Eğer kimlik doğrulama başarılıysa, yani kullanıcı geçerliyse, bu blok çalışır.
            return new ResponseEntity<>(jwtService.generateToken(authentication), // Bu metodun çağrılmasıyla, geçerli kullanıcının kimlik bilgileri kullanılarak bir JWT (JSON Web Token) oluşturulur.
                    HttpStatus.OK);
        }

        //Eğer kimlik doğrulama başarısız olursa, yetersiz veya hatalı kullanıcı bilgileri nedeniyle bir
        throw new UsernameNotFoundException("invalid user details");
    }

    public void logout(HttpServletResponse response) {
        // JWT token'ını geçersiz kıl
        JwtService.invalidateToken(response);

    }

}
