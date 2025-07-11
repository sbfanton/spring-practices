package com.sbfanton.oauth.oauthclient.controller;

import com.sbfanton.oauth.oauthclient.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mails")
public class EmailController {

    @Autowired
    private UserService userService;

    @GetMapping("/validate")
    public ResponseEntity<?> validateEmail(@RequestParam String token) throws Exception {
        return ResponseEntity.ok()
                .body(userService.validateEmail(token));
    }

    @PostMapping("/resend-validation")
    public ResponseEntity<String> resendValidation(@RequestParam String email) throws Exception {
        return ResponseEntity.ok()
                .body(userService.resendVerificationEmail(email));
    }
}
