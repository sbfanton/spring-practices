package com.oauth2.springoauth2clientintro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myApp")
public class HomeController {

	@GetMapping("/hello")
	public String hello() {
		return "Hola Mundo!";
	}
	
	@GetMapping("/helloSecured")
	public String helloSecured() {
		return "Hola Mundo Seguro!";
	}
}
