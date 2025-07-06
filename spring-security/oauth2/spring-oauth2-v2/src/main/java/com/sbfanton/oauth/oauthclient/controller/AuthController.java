package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.model.dto.AuthResponseDTO;
import com.sbfanton.oauth.oauthclient.model.dto.LoginDTO;
import com.sbfanton.oauth.oauthclient.model.dto.RegisterDTO;
import com.sbfanton.oauth.oauthclient.service.JwtService;
import com.sbfanton.oauth.oauthclient.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginDTO loginDTO) throws Exception {
        AuthResponseDTO authResponseDTO = userService.login(loginDTO);
        Map<String, ResponseCookie> cookiesMap = jwtService.generateTokenCookies(authResponseDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookiesMap.get("access_token").toString())
                .header(HttpHeaders.SET_COOKIE, cookiesMap.get("refresh_token").toString())
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> login(
            @RequestBody @Valid RegisterDTO registerDTO) throws Exception {
        AuthResponseDTO authResponseDTO = userService.register(registerDTO);
        Map<String, ResponseCookie> cookiesMap = jwtService.generateTokenCookies(authResponseDTO);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookiesMap.get("access_token").toString())
                .header(HttpHeaders.SET_COOKIE, cookiesMap.get("refresh_token").toString())
                .build();
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        jwtService.invalidateTokenCookies(response);
        return ResponseEntity.noContent().build();
    }
}
