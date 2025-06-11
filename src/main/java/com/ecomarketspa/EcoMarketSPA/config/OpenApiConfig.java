package com.ecomarketspa.EcoMarketSPA.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // Configuración de titulo de la api y contacto
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API 2025 EcoMarketSpA")
                        .version("1.0")
                        .description("Documentación de la API REST de EcoMarketSpA")
                        .termsOfService("https://ecomarketspa.cl/documentos")
                        .contact(new Contact()
                                .name("Jean Pierre Valenzuela")
                                .email("jorgevergaraecomarket@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }

    // Agrupación de productos
    @Bean
    public GroupedOpenApi productosApi() {
        return GroupedOpenApi.builder()
                .group("productos")
                .pathsToMatch("/api/productos/**")
                .build();
    }

    // Agrupacion de usuario
    @Bean
    public GroupedOpenApi usuariosApi() {
        return GroupedOpenApi.builder()
                .group("usuarios")
                .pathsToMatch("/api/users/**")
                .build();
    }
}
