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
    private String githubClientId;

    @Value("${oauth2.github.client-secret}")
    private String githubClientSecret;

    @Value("${oauth2.github.authorization-uri}")
    private String githubAuthorizationUri;

    @Value("${oauth2.github.token-uri}")
    private String githubTokenUri;

    @Value("${oauth2.github.user-info-uri-1}")
    private String githubUserInfoUri1;

    @Value("${oauth2.github.redirect-uri}")
    private String githubRedirectUri;

    @Value("${oauth2.github.user-name-attribute-name}")
    private String githubUserNameAttributeName;

    @Value("${oauth2.google.client-id}")
    private String googleClientId;

    @Value("${oauth2.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth2.google.authorization-uri}")
    private String googleAuthorizationUri;

    @Value("${oauth2.google.token-uri}")
    private String googleTokenUri;

    @Value("${oauth2.google.user-info-uri-1}")
    private String googleUserInfoUri1;

    @Value("${oauth2.google.redirect-uri}")
    private String googleRedirectUri;

    @Value("${oauth2.google.user-name-attribute-name}")
    private String googleUserNameAttributeName;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> clientRegistrationList = List.of(
            githubClientRegistration(), googleClientRegistration()
        );

        return new InMemoryClientRegistrationRepository(clientRegistrationList);
    }

	private ClientRegistration githubClientRegistration() {
        return ClientRegistration.withRegistrationId("github-client")
            .clientId(githubClientId)
            .clientSecret(githubClientSecret)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri(baseUrl + githubRedirectUri)
            .scope("read:user", "user:email")
            .authorizationUri(githubAuthorizationUri)
            .tokenUri(githubTokenUri)
            .userInfoUri(githubUserInfoUri1)
            .userNameAttributeName(githubUserNameAttributeName)
            .clientName("GitHub")
            .build();
    }

    private ClientRegistration googleClientRegistration() {
        return ClientRegistration.withRegistrationId("google-client")
                .clientId(googleClientId)
                .clientSecret(googleClientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(baseUrl + googleRedirectUri)
                .scope("openid", "profile", "email")
                .authorizationUri(googleAuthorizationUri)
                .tokenUri(googleTokenUri)
                .userInfoUri(googleUserInfoUri1)
                .userNameAttributeName(googleUserNameAttributeName)
                .clientName("Google")
                .build();
    }
}
