package com.x.rentacar.config;

import com.x.rentacar.filter.JwtAuthFilter;
import com.x.rentacar.service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    UserDetailsService userDetailsService() { return new UserInfoUserDetailsService(); }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(a -> a // HTTP isteklerinin yetkilendirme ayarlarını yapılandırır. authorizeHttpRequests metodu, istekleri hangi koşullar altında yetkilendireceğimizi belirlememize olanak tanır.
                .requestMatchers("/customer/addCustomer", "/customer/getToken", "/customer/log", "/test/**").permitAll()
                // Belirli URL path'lerine yapılan istekleri herkese (authenticated veya anonymous) izin verir.
                .requestMatchers(AUTH_WHITE_LIST).permitAll()
                // AUTH_WHITE_LIST içinde belirtilen URL path'lerine yapılan istekleri herkese izin verir.
                .anyRequest().authenticated())
                // Diğer tüm istekleri ise sadece kimlik doğrulaması yapılmış (authenticated) kullanıcılara izin verir.
                .sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                // Session yönetimini yapılandırır ve uygulama için Stateless (durumsuz) bir politika belirler. Bu, kullanıcı oturumlarını tutmamız gerekmeyecek anlamına gelir.
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
                // Bu genellikle özel bir kimlik doğrulama filtresini veya token tabanlı kimlik doğrulama süreçlerini entegre etmek için kullanılır.
        return http.build();
        // Bu metod, yapılandırılan HttpSecurity nesnesini döndürerek SecurityFilterChain bean'ini oluşturur. Bu yapılandırma, uygulamanın güvenlik ayarlarını belirler ve bu ayarlar, uygulamanın belirli HTTP isteklerine nasıl yanıt vereceğini tanımlar.
    }

    // Bu URL'lerin AUTH_WHITE_LIST içinde olması, güvenlik konfigürasyonunda belirli bir yetkilendirme gereksinimi olmaksızın herkese açık olmalarını sağlamak amacını taşır.
    // Swagger ile bağlantı için gereklidir.
    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    //Bu yapı, genellikle kullanıcıların parolalarını güvenli bir şekilde depolamak için kullanılır. Kullanıcı şifreleri, veritabanında depolanmadan önce bu PasswordEncoder kullanılarak hashlenir ve daha sonra giriş sırasında karşılaştırılır. BCryptPasswordEncoder'ın tercih edilmesinin nedeni, bcrypt algoritmasının güçlü bir şekilde tasarlanmış olması ve kriptografik olarak güvenli olmasıdır.
    @Bean
    PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // kullanıcı kimlik doğrulama işlemleri için bir DAO (Data Access Object) kullanır ve genellikle kullanıcı bilgilerini bir UserDetailsService implementasyonu aracılığıyla sağlar.
        authenticationProvider.setUserDetailsService(userDetailsService());
        // Bu satırda, oluşturulan DaoAuthenticationProvider'a bir UserDetailsService eklenir. userDetailsService() metodu, uygulamada tanımlanmış olan kullanıcı detaylarını yükleyen bir UserDetailsService bean'ini döndürür.
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        // Bu, kullanıcı şifrelerini doğrulama ve kayıt işlemleri sırasında kullanılır.
        return authenticationProvider;
        // Bu bean, genellikle Spring Security konfigürasyonunda kullanılarak kullanıcı kimlik doğrulama ayarlarını sağlamak için kullanılır.

    }

    // Bu kod, genellikle özel kimlik doğrulama ihtiyaçları olan projelerde kullanılır.
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
