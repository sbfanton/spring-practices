package com.sbfanton.oauth.resourceserver.config;

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
	 *  ¿Qué logra esta configuración?
		Con esto, tu API:
		Rechaza accesos sin token o con token inválido (firma, expiración, etc.).
		Acepta únicamente JWTs válidos emitidos por el servidor de autorización.
		Puede luego, si querés, hacer cosas más avanzadas como:
		- Validar scopes.
		- Aplicar reglas según roles (claims del token).
	 * */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	    
	    /*
	     * Los siguientes scopes deben estar definidos en el servidor de autorizacion
	     * */
	        .authorizeHttpRequests(authorize -> authorize
	        	.requestMatchers(HttpMethod.GET, "/resources/**").hasAuthority("SCOPE_read")
	        	.requestMatchers(HttpMethod.POST, "/resources/**").hasAuthority("SCOPE_write")
	            .anyRequest().authenticated()
	        )

	        /*
	         * Habilita el modo "resource server" en Spring Security.
				Específicamente, le está diciendo: "los tokens de acceso que me manden 
				deben ser JWTs, y quiero que los verifiques automáticamente".
				
				¿Qué hace Customizer.withDefaults()?
				Usa la configuración por defecto para la validación del JWT:
				
				- Busca el issuer-uri en el archivo application.properties.
				- Spring descargará desde ese issuer un documento llamado 
				  .well-known/openid-configuration, y de allí extraerá los jwks_uri 
				  para validar la firma de los tokens.
				- Obtiene las claves públicas (JWKs) del servidor de autorización.
				- Valida la firma, el issuer, la expiración, etc.
				
	         * */
	        .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
	    
	    return http.build();
	}
}
