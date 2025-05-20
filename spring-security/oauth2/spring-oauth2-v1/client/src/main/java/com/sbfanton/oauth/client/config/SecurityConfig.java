package com.sbfanton.oauth.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${authorization.server}")
	private String authServer;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(request -> {
            	request.requestMatchers(HttpMethod.GET, "/authorized/**").permitAll()
                .anyRequest().authenticated();
            })
            .oauth2Login(oauth2 -> oauth2
                    .loginPage("/oauth2/authorization/oauth-client"))  // Configura el login con OAuth2
        	.oauth2Client(Customizer.withDefaults());
        	
        return http.build();
    }
	
	@Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("oauth-client")
            .clientId("oauth-client")
            .clientSecret("12345678910")
            .clientName("oauth-client")
            .providerConfigurationMetadata(null)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .scope("openid", "profile", "read", "write")
            //.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .redirectUri("http://127.0.0.1:8080/authorized")
            .authorizationUri(authServer + "/oauth2/authorize")
            .tokenUri(authServer + "/oauth2/token")
            .userInfoUri(authServer + "/oauth2/userinfo")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .issuerUri(authServer)
            .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    
}
