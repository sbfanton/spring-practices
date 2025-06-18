package com.sbfanton.oauth.oauthclient.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class OAuth2ClientConfig {

    @Value("${base.url}")
    private String baseUrl;

    @Value("${oauth2.github.client-id}")
    private String clientId;

    @Value("${oauth2.github.client-secret}")
    private String clientSecret;

    @Value("${oauth2.github.authorization-uri}")
    private String authorizationUri;

    @Value("${oauth2.github.token-uri}")
    private String tokenUri;

    @Value("${oauth2.github.user-info-uri-1}")
    private String userInfoUri1;

    @Value("${oauth2.github.redirect-uri}")
    private String redirectUri;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> clientRegistrationList = List.of(
            githubClientRegistration()
        );

        return new InMemoryClientRegistrationRepository(clientRegistrationList);
    }

	private ClientRegistration githubClientRegistration() {
        return ClientRegistration.withRegistrationId("github-client")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri(baseUrl + redirectUri)
            .scope("read:user", "user:email")
            .authorizationUri(authorizationUri)
            .tokenUri(tokenUri)
            .userInfoUri(userInfoUri1)
            .userNameAttributeName("id")
            .clientName("GitHub")
            .build();
    }
}
