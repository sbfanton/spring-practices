package com.course.springsecuritycourse.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.springsecuritycourse.dto.AuthResponseDTO;
import com.course.springsecuritycourse.dto.LoginDTO;
import com.course.springsecuritycourse.dto.RegisterDTO;
import com.course.springsecuritycourse.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("${corporation.api}/auth")
public class AuthenticationController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
        return new ResponseEntity<AuthResponseDTO>(
            authService.login(loginDTO), 
            HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid RegisterDTO registerDTO) {
        return new ResponseEntity<AuthResponseDTO>(
            authService.register(registerDTO), 
            HttpStatus.OK);
    }
}
