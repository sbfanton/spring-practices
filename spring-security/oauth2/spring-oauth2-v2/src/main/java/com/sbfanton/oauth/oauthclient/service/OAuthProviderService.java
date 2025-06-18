package com.sbfanton.oauth.oauthclient.service;

import com.sbfanton.oauth.oauthclient.model.OAuthEndpoint;
import com.sbfanton.oauth.oauthclient.model.OAuthProvider;
import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.utils.constants.AuthProviderType;
import com.sbfanton.oauth.oauthclient.utils.constants.OAuthConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Value("${oauth2.github.user-info-uri-1}")
    private String userInfoUriGithub1;

    @Value("${oauth2.github.user-info-uri-2}")
    private String userInfoUriGithub2;

    @Value("${base.url}")
    private String baseUrl;

    private Map<String, OAuthProvider> providers;

    @PostConstruct
    public void init() {
        List<OAuthEndpoint> githubUserInfoUris = new ArrayList<>();
        githubUserInfoUris.add(new OAuthEndpoint(userInfoUriGithub1, false));
        githubUserInfoUris.add(new OAuthEndpoint(userInfoUriGithub2, true));

        providers = Map.of(
                OAuthConstants.GITHUB_PROVIDER,
                OAuthProvider.builder()
                        .clientId(clientIdGithub)
                        .clientSecret(clientSecretGithub)
                        .authorizationUri(authorizationUriGithub
                            + "?client_id=" + clientIdGithub
                            + "&redirect_uri=" + URLEncoder.encode(redirectUriGithub, StandardCharsets.UTF_8)
                            + "&scope=user")
                        .userInfoUris(githubUserInfoUris)
                        .redirectUri(redirectUriGithub)
                        .tokenUri(tokenUriGithub)
                        .userInfoFields(List.of("login", "avatar_url", "html_url", "token", "email", "primary", "verified"))
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
            case OAuthConstants.GITHUB_PROVIDER -> getGithubUser(userInfo);
            default -> null;
        };
    }

    private User getGithubUser(Map<String, Object> userInfo) {

        List<String> listEndpoints = providers.get(OAuthConstants.GITHUB_PROVIDER)
                .getUserInfoUris().stream()
                .filter(OAuthEndpoint::isReturnsList)
                .map(OAuthEndpoint::getUrl)
                .toList();

        String email = ((List<Map>)userInfo.get(listEndpoints.get(0)))
                .stream()
                .filter(e -> Boolean.TRUE.equals(e.get("primary")) && Boolean.TRUE.equals(e.get("verified")))
                .map(e -> (String) e.get("email"))
                .findFirst()
                .orElse("No hay mail registrado");

        return User.builder()
                .username((String) userInfo.get("login"))
                .avatarUrl((String) userInfo.get("avatar_url"))
                .web((String) userInfo.get("html_url"))
                .provider(AuthProviderType.GITHUB)
                .providerId(String.valueOf((Integer)userInfo.get("id")))
                .email(email)
                .build();
    }
}
