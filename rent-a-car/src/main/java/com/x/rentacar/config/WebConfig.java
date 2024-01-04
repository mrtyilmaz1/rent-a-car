package com.x.rentacar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//jwt token CORS hatasından dolayı
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //Bu yapılandırma, uygulamanın CORS politikalarını genişletmiş ve farklı orijinlerden gelen taleplere izin vermiştir. Bu genellikle API'lerin dışarıdan erişilebilir olmasını sağlamak için kullanılır.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // CORS ayarlşarının uygulanacığı deseni kapsar. ** tamamı.
                .allowedOriginPatterns("*") // burada tüm orijinlere izin verildiği anlamına gelir.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İzin verilen HTTP metotları.
                .allowedHeaders("*") // İzin verilen HTTP başlıkları.
                .allowCredentials(true); // Tarayıcı tarafından sağlanan kimlik bilgilerine (örneğin, çerezler) izin verilip verilmeyeceğini belirler.
    }

    // Bu yapılandırma, uygulamanın genel CORS politikalarını belirler ve farklı orijinlerden gelen HTTP taleplerine izin verir. Bu tür bir yapılandırma genellikle Spring Security veya diğer güvenlik önlemleriyle birlikte kullanılarak uygulamalarda güvenlik sağlamak amacıyla kullanılır.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // UrlBasedCorsConfigurationSource sınıfı: Bu sınıf, URL tabanlı bir CORS konfigürasyon kaynağını temsil eder. Yani, belirli URL desenleri için CORS konfigürasyonunu tanımlamak için kullanılır.
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); //Tüm orijinlere izin verilmesini belirtir.
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // Bu yapılandırma, Spring uygulamanızda CORS politikalarını etkinleştirmek için kullanılır. CorsFilter, gelen taleplerdeki CORS başlıklarını kontrol eder ve belirtilen konfigürasyona göre bu başlıklara izin verir veya reddeder. Bu sayede, tarayıcıdaki güvenlik politikalarını ve CORS kısıtlamalarını geçerek farklı orijinlerden gelen HTTP taleplerine uygun bir şekilde yanıt verilebilir.
    @Bean
    public CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter(corsConfigurationSource());
        return filter;
    }

}
