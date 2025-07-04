package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.facade.CallbackServiceFacade;
import com.sbfanton.oauth.oauthclient.model.OAuthProvider;
import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.AuthResponseDTO;
import com.sbfanton.oauth.oauthclient.service.JwtService;
import com.sbfanton.oauth.oauthclient.service.OAuthProviderService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            @RequestParam(required = true) String provider,
            HttpServletResponse response
    ) throws Exception {
        String authUrl = oAuthProviderService.getProvider(provider).getAuthorizationUri();
        if(authUrl == null)
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Proveedor no soportado");
        response.sendRedirect(authUrl);
    }

    @GetMapping("/callback")
    public void handleCallback(@RequestParam(required = true) String code,
                               @RequestParam(required = true) String provider,
                               HttpServletResponse response)
    throws Exception {
        callbackServiceFacade.processProviderCallbackAndGetToken(provider, code, response);
        response.sendRedirect(frontUrl + "/auth/callback");
    }
}

