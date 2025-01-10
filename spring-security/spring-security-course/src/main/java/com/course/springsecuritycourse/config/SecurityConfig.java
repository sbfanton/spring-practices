package com.course.springsecuritycourse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Value("${corporation.api}")
    private String corporationApi;

    @Autowired
    private AuthenticationProvider authProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

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
            .sessionManagement( //como utilizamos jwt y no sesiones a traves de cookies o sessions, ponemos que la sesion es sin estado
                sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authProvider) // seteamos el proveedor que se encargará del login y verificar las credenciales
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // seteamos el filtro que utilizaremos para validar y verificar los jwt
            .build();
    }
}
