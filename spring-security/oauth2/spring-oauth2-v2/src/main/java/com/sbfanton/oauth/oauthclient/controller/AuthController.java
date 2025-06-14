package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.exception.ServiceException;
import com.sbfanton.oauth.oauthclient.model.User;
import com.sbfanton.oauth.oauthclient.model.dto.AuthResponseDTO;
import com.sbfanton.oauth.oauthclient.model.dto.LoginDTO;
import com.sbfanton.oauth.oauthclient.model.dto.RegisterDTO;
import com.sbfanton.oauth.oauthclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws Exception {
        AuthResponseDTO authResponseDTO = userService.login(loginDTO);
        return ResponseEntity.ok(authResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody RegisterDTO registerDTO) throws Exception {
        AuthResponseDTO authResponseDTO = userService.register(registerDTO);
        return ResponseEntity.ok(authResponseDTO);
    }
}
