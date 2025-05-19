package com.sbfanton.oauth.resourceserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
public class ResourceController {

	@GetMapping("/user")
	public ResponseEntity<?> readUser(Authentication authentication) {
		
		return ResponseEntity.ok("The user can read: " + authentication.getAuthorities());
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> writeUser(Authentication authentication) {
		
		return ResponseEntity.ok("The user can write: " + authentication.getAuthorities());
	}
}
