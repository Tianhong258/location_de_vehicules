package com.accenture.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration{

    @Bean
    public OpenAPI apiConfiguration() {
        return new OpenAPI()
                .info(new Info()
                        .title("Location de Véhicule")
                        .description("Api pour l'application Location de Véhicule")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Tianhong Huang").
                                email("tianhong.huang@accenture.com"))
                );
    }
}