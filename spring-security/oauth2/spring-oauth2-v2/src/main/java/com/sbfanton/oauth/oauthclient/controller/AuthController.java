package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.model.dto.AuthResponseDTO;
import com.sbfanton.oauth.oauthclient.model.dto.LoginDTO;
import com.sbfanton.oauth.oauthclient.model.dto.RegisterDTO;
import com.sbfanton.oauth.oauthclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        AuthResponseDTO authResponseDTO = userService.login(loginDTO);
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody RegisterDTO registerDTO) {
        AuthResponseDTO authResponseDTO = userService.register(registerDTO);
        return ResponseEntity.ok(authResponseDTO);
    }
}
