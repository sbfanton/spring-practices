package com.sbfanton.oauth.client.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello!");
	}
	
	@GetMapping("/authorized")
	public Map<String, String> authorized(@RequestParam("code") String authorizationCode) {
		return Collections.singletonMap("authorizationCode", authorizationCode);
	}
}
