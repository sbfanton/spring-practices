package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.model.dto.StatusDTO;
import com.sbfanton.oauth.oauthclient.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;

@RestController
@RequestMapping("/mails")
public class EmailController {

    @Autowired
    private UserService userService;

    @Value("${front.url}")
    private String frontUrl;

    @GetMapping("/validate")
    public ResponseEntity<?> validateEmail(
            @RequestParam String token,
            HttpServletResponse response) throws Exception {

        userService.validateEmail(token, response);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(frontUrl + "/email-verified"))
                .build();
    }

    @GetMapping("/email-validated-status")
    public ResponseEntity<?> checkEmailVerifiedCookie(
            @CookieValue(name = "email_verified", required = false) String value) {
        if ("true".equals(value)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/resend-validation")
    public ResponseEntity<?> resendValidation(@RequestParam String email) throws Exception {
        return ResponseEntity.ok()
                .body(userService.resendVerificationEmail(email));
    }
}
