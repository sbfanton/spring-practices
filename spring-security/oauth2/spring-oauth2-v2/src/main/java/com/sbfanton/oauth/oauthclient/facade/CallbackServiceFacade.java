package com.sbfanton.oauth.oauthclient.facade;

import com.sbfanton.oauth.oauthclient.exception.ServiceException;
import com.sbfanton.oauth.oauthclient.model.OAuthProvider;
import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.service.JwtService;
import com.sbfanton.oauth.oauthclient.service.OAuthProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CallbackServiceFacade {

    @Autowired
    private OAuthProviderService oAuthProviderService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RestTemplate restTemplate;

    public String processProviderCallbackAndGetToken(String provider, String code) throws Exception {
        OAuthProvider oAuthProvider = oAuthProviderService.getProvider(provider);
        if(oAuthProvider == null) {
            throw new ServiceException("Provider no soportado: " + provider);
        }

        String accessToken = getTokenFromAuthServer(code, oAuthProvider, provider);
        ResponseEntity<Map> userInfo = getUserInfoFromAuthServer(accessToken, oAuthProvider);
        User user = oAuthProviderService.getUserByProviderUserInfo(provider, userInfo.getBody());
        return jwtService.getToken(user, true);
    }

    private String getTokenFromAuthServer(String code, OAuthProvider oAuthProvider, String provider) throws ServiceException {

        // Intercambiar código por access token
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", oAuthProvider.getClientId());
        params.add("client_secret", oAuthProvider.getClientSecret());
        params.add("code", code);
        params.add("redirect_uri", oAuthProvider.getRedirectUri());
        params.add("grant_type", oAuthProvider.getGrantType());

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                oAuthProvider.getTokenUri(),
                tokenRequest,
                Map.class);

        if (!tokenResponse.getStatusCode().is2xxSuccessful() || tokenResponse.getBody().get("error") != null) {
            throw new ServiceException((String) tokenResponse.getBody().get("error"));
        }

        return oAuthProviderService.extractTokenFromResponse(provider, tokenResponse.getBody());
    }

    private ResponseEntity<Map> getUserInfoFromAuthServer(String token, OAuthProvider oAuthProvider) throws ServiceException {

        // Obtener info del usuario
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        userHeaders.setBearerAuth(token);
        userHeaders.set("User-Agent", "OAuth2-V2-app");

        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

        ResponseEntity<Map> userResponse = restTemplate.exchange(
                oAuthProvider.getUserInfoUri(),
                HttpMethod.GET,
                userRequest,
                Map.class
        );

        if (!userResponse.getStatusCode().is2xxSuccessful()) {
            throw new ServiceException("Error al obtener datos del usuario");
        }

        return userResponse;
    }
}
