package com.employeemanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenAPIConfig {

    @Value("${app.creator.name}")
    private String ownerName;
    @Value("${app.creator.email}")
    private String ownerEmail;
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info().title("Employee Management API")
                .description("This API exposes endpoints to Employee Manage Service.")
                .version("v0.0.1")
                .contact(getContactDetails())
                .license(getLicenseDetails()));
    }

    private Contact getContactDetails() {
        return new Contact().name(ownerName)
                .email(ownerEmail)
                .url("");
    }

    private License getLicenseDetails() {
        return new License().name("GNU General Public License v3.0")
                .url("");
    }
}
