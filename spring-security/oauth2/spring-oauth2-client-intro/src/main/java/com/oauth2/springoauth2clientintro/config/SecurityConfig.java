package com.oauth2.springoauth2clientintro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/*
	 * Actualmente, SpringBoot incluye 4 proveedores de autenticacion por defecto por OAuth2:
	 * - Google
	 * - Github
	 * - Facebook
	 * - Okta
	 * 
	 * Aqui usaremos dos de ellos: Google y Github
	 * 
	 * Igualmente, nosotros tambien podriamos crear nuestros proveedores con otros mecanismos
	 * */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.authorizeHttpRequests(request -> {
					request.requestMatchers(HttpMethod.GET, "/", "/hello").permitAll();
					request.anyRequest().authenticated();
				})
				.formLogin(Customizer.withDefaults())
				.oauth2Login(Customizer.withDefaults())
				.build();
	}
	
}
