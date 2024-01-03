package com.x.rentacar.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    // Bu metot, API anahtarları veya JWT (JSON Web Token) gibi güvenlik şemalarını oluşturan bir yardımcı metottur.

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP) // Güvenlik şemasının tipi belirlenir. Bu durumda, HTTP tabanlı bir güvenlik şeması kullanıldığı belirtilir.
                .bearerFormat("JWT") // Bu metotta, JWT formatının kullanıldığı belirtilmiştir.
                .scheme("bearer"); //  Güvenlik şemasının adı belirlenir.
    }

    @Bean
    // Bu annotation, Spring tarafından yönetilen bir bileşeni tanımlar. Bu durumda, OpenAPI türünde bir bileşeni belirtir.
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement(). //API'nin güvenlik gereksinimlerini belirtir. Bu örnekte, "Bearer Authentication" adında bir güvenlik gereksinimi eklenmiştir. Bu gereksinim, API'ye erişimde kullanılacak güvenlik şemasını ifade eder.
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme())) //Bu örnekte, "Bearer Authentication" adında bir güvenlik şeması eklenmiştir ve bu şema, createAPIKeyScheme() metodu tarafından oluşturulan bir güvenlik şemasıdır.
                .info(new Info().title("My REST API") // Bu bölümde API'nin başlığı, açıklaması, sürümü, iletişim bilgileri ve lisans bilgileri tanımlanır.
                        .description("Some custom description of API.")
                        .version("1.0").contact(new Contact().name("Murat Yılmaz")
                                .email( "muryilmaz95@gmail.com").url("bilgeadam.com"))
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }

}
