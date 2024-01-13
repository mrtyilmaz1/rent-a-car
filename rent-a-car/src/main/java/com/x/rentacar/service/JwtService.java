package com.x.rentacar.service;

import com.x.rentacar.dto.LoginDto;
import com.x.rentacar.model.Customer;
import com.x.rentacar.repository.CustomerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final CustomerRepository customerRepository;

    public static final String SECRET = "404D635166546A576E5A7234753778214125442A472D4B6150645267556B5870";

    public String extractEmail(String token){ return extractClaim(token, Claims::getSubject); }
    // bu metodun amacı JWT'den emaili çıkarmaktır.

    public Date extractExpiration(String token){ return extractClaim(token, Claims::getExpiration); }
    // bu metodun amacı JWT'den son kullanma tarihini çıkarmaktır.

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaim(token);
        // JWT'den tüm bilgileri çıkarmak için bir yardımcı metod olan extractAllClaim metodunu çağır.
        return claimsResolver.apply(claims);
        // Parametre olarak alınan claimsResolver fonksiyonunu kullanarak istenen bilgiyi çıkart ve geri döndür.
    }

    private Claims extractAllClaim(String token){
        return Jwts.parserBuilder()     // JWT parser'ını oluştur
                .setSigningKey(getSignKey()).build() // İmza anahtarını almak için getSignKey() metodunu kullan
                .parseClaimsJws(token).getBody();
        // Verilen JWT'yi parse et ve içindeki talepleri (claims) elde et
    }

    // JWT'nin son kullanma tarihini çıkart ve şu anki tarihten önce mi kontrol et
    private Boolean isTokenExpired(String token){return extractExpiration(token).before(new Date()); }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String email = extractEmail(token); // JWT'den e-posta adresini çıkart
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
        // Eğer e-posta adresi UserDetails nesnesinin kullanıcı adına (username) eşleşiyorsa ve token henüz geçerliyse, true döndür
    }


    public LoginDto generateToken(Authentication authentication){
        log.info("generateToken içine girdi.");
        LoginDto loginDto = new LoginDto();
        Optional<Customer> customerOptional = customerRepository.findByEmail(authentication.getName());
        // Kullanıcının e-posta adresine göre müşteriyi arar

        Map<String, Object> claims = new HashMap<>();
        // JWT içinde yer alacak talepler (claims) için bir Map oluşturulur
        claims.put("authorities", authentication.getAuthorities());
        claims.put("name", authentication.getName());
        // Authentication nesnesinden alınan yetkiler (authorities) ve kullanıcı adı (name) claims'e eklenir
        if (customerOptional.isPresent()){
            loginDto.setCustomerId(customerOptional.get().getId());
        }
        // Eğer müşteri mevcutsa, müşteri kimliği (customerId) LoginDto'ya eklenir
        loginDto.setToken(createToken(claims, authentication.getName()));
        // Token oluşturulup LoginDto'nun içine yerleştirilir
        // log.info(loginDto.getToken()); logout kontrolü için kullanılıyor.
        return loginDto;
    }

    private String createToken(Map<String,Object> claims, String email){
        return Jwts.builder().setClaims(claims)    // JWT (JSON Web Token) oluşturulur
                .setSubject(email) // JWT'nin konusu (subject) belirtilir, genellikle kullanıcı adı veya e-posta adresi kullanılır
                .setIssuedAt(new Date(System.currentTimeMillis())) // JWT'nin oluşturulma tarihi belirtilir
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30)) // JWT'nin süresi belirtilir, burada 30 dakika (1000 milisaniye * 60 saniye * 30 dakika) olarak belirlenmiştir
                .signWith(SignatureAlgorithm.HS256, getSignKey()) // JWT'nin imzalanması için kullanılacak algoritma ve anahtar belirtilir
                // HS256 şifreleme algoritmasıdır. Farklı versiyonları vardır. 256, 256 byteı ifade eder. HS256 en yaygın olanıdır.
                .compact(); // JWT string haline getirilir (compact)
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        // SECRET değişkeninden alınan BASE64 kodlu anahtarın byte dizisini elde eder
        return Keys.hmacShaKeyFor(keyBytes);
        // Elde edilen byte dizisinden bir HMAC-SHA anahtarı oluşturur ve geri döndürür
    }

    public static void invalidateToken(HttpServletResponse response) {


        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setMaxAge(0); // Çerezin geçerlilik süresi (max age) sıfıra ayarlanır. Bu, tarayıcıya çerezin hemen silinmesi gerektiğini söyler.
        cookie.setHttpOnly(false); // swagerde kullanabilmek için.
        cookie.setPath("/"); // Çerezin geçerli olduğu URL yolu belirlenir. "/" ifadesi, çerezin uygulamanın kök dizininden geçerli olmasını sağlar.
        response.addCookie(cookie); // Belirtilen çerez, HTTP yanıtına eklenir ve istemciye gönderilir.
        /* LoginDto loginDto = new LoginDto();
           log.info(loginDto.getToken()); logout kontrolü için kullanılıyor. */
        log.info("token sıfırlandı.");
    }


}


