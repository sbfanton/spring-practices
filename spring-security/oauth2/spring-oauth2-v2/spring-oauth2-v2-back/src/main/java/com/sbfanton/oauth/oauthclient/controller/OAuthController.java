package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.exception.OAuthException;
import com.sbfanton.oauth.oauthclient.facade.CallbackServiceFacade;
import com.sbfanton.oauth.oauthclient.service.OAuthProviderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/oauth2")
public class OAuthController {

    @Autowired
    private OAuthProviderService oAuthProviderService;

    @Autowired
    private CallbackServiceFacade callbackServiceFacade;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${front.url}")
    private String frontUrl;

    @GetMapping("/login")
    public void redirectToProvider(
            @RequestParam String provider,
            HttpServletResponse response
    ) throws Exception {
        try {
            String authUrl = oAuthProviderService.getProvider(provider).getAuthorizationUri();
            if (authUrl == null) {
                throw new OAuthException("Proveedor no soportado: " + provider);
            }
            response.sendRedirect(authUrl);
        } catch (Exception ex) {
            throw new OAuthException(ex.getMessage(), ex);
        }
    }

    @GetMapping("/callback")
    public void handleCallback(
            @RequestParam String code,
            @RequestParam String provider,
            HttpServletResponse response
    ) throws Exception {
        try {
            callbackServiceFacade.processProviderCallbackAndGetToken(provider, code, response);
            response.sendRedirect(frontUrl + "/auth/callback");
        } catch (Exception ex) {
            throw new OAuthException(ex.getMessage(), ex);
        }
    }
}

