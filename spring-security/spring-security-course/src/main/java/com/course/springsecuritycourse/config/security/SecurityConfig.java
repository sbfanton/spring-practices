package com.course.springsecuritycourse.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Value("${corporation.api}")
    private String corporationApi;

    /*
    Aqui estamos creando la cadena de filtros de seguridad que se aplicaran 
    a las request para endpoints tanto publicos como protegidos, y en el 
    caso de los protegidos qué mecanismos se implementarán para verificar 
    la autenticidad y autorizacion
    */ 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(crsf -> crsf.disable()) //deshabilita una verificacion que pide token crsf para metodos post
            .authorizeHttpRequests(
                authreq -> authreq.requestMatchers(corporationApi + "/auth/**").permitAll() //endpoints publicos
                                  .anyRequest().authenticated() //endpoints protegidos por los filtros de seguridad
            )
            .formLogin(withDefaults())
            .build();
    }
}
