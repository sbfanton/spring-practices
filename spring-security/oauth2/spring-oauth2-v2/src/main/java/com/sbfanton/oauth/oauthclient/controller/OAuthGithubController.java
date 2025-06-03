package com.sbfanton.oauth.oauthclient.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oauth2/github")
public class OAuthGithubController {

    @Value("${oauth2.github.client-id}")
    private String clientId;

    @Value("${oauth2.github.client-secret}")
    private String clientSecret;

    @Value("${oauth2.github.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.github.token-uri}")
    private String tokenUri;

    @Value("${oauth2.github.authorization-uri}")
    private String authorizationUri;

    @Value("${oauth2.github.user-info-uri}")
    private String userInfoUri;

    @Value("${base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/login")
    public void redirectToGitHub(HttpServletResponse response) throws IOException, IOException {
        String authUrl = authorizationUri
                + "?client_id=" + clientId
                + "&redirect_uri=" + URLEncoder.encode(baseUrl + redirectUri, StandardCharsets.UTF_8)
                + "&scope=user";
        response.sendRedirect(authUrl);
    }

    @GetMapping("/callback")
    public ResponseEntity<?> handleGitHubCallback(@RequestParam String code) {
        // Intercambiar el authorization_code por un access_token
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("redirect_uri", baseUrl + redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                tokenUri,
                request,
                Map.class
        );

        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/user-info")
    public ResponseEntity<Map> getGithubUserInfo(@RequestHeader("Authorization") String authHeader) {

        String token = "";

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token);
        headers.set("User-Agent", "OAuth2-V2-app");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                request,
                Map.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}

