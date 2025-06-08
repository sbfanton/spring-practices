package com.sbfanton.oauth.oauthclient.service;

import com.sbfanton.oauth.oauthclient.model.OAuthProvider;
import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.utils.constants.AuthProviderType;
import com.sbfanton.oauth.oauthclient.utils.constants.OAuthConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
public class OAuthProviderService {

    @Value("${oauth2.github.client-id}")
    private String clientIdGithub;

    @Value("${oauth2.github.client-secret}")
    private String clientSecretGithub;

    @Value("${oauth2.github.redirect-uri}")
    private String redirectUriGithub;

    @Value("${oauth2.github.token-uri}")
    private String tokenUriGithub;

    @Value("${oauth2.github.authorization-uri}")
    private String authorizationUriGithub;

    @Value("${oauth2.github.user-info-uri}")
    private String userInfoUriGithub;

    @Value("${base.url}")
    private String baseUrl;

    private Map<String, OAuthProvider> providers;

    @PostConstruct
    public void init() {
        providers = Map.of(
                OAuthConstants.GITHUB_PROVIDER,
                OAuthProvider.builder()
                        .clientId(clientIdGithub)
                        .clientSecret(clientSecretGithub)
                        .authorizationUri(authorizationUriGithub
                            + "?client_id=" + clientIdGithub
                            + "&redirect_uri=" + URLEncoder.encode(redirectUriGithub, StandardCharsets.UTF_8)
                            + "&scope=user")
                        .userInfoUri(userInfoUriGithub)
                        .redirectUri(redirectUriGithub)
                        .tokenUri(tokenUriGithub)
                        .userInfoFields(List.of("login", "avatar_url", "html_url", "token"))
                        .build()
        );
    }

    public OAuthProvider getProvider(String provider) {
        return providers.get(provider);
    }

    public String extractTokenFromResponse(String provider, Map body) {
        return switch (provider) {
            case OAuthConstants.GITHUB_PROVIDER -> (String) body.get("access_token");
            default -> null;
        };
    }

    public User getUserByProviderUserInfo(String provider, Map<String, Object> userInfo) {
        return switch(provider) {
            case OAuthConstants.GITHUB_PROVIDER -> User.builder()
                                                       .username((String) userInfo.get("login"))
                                                       .avatarUrl((String) userInfo.get("avatar_url"))
                                                       .web((String) userInfo.get("html_url"))
                                                       .provider(AuthProviderType.GITHUB)
                                                       .build();
            default -> null;
        };
    }
}
