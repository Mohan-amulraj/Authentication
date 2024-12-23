package com.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin")
public class AdminController {

	@GetMapping
	public ResponseEntity<String> adminLogin() {
		return ResponseEntity.ok("Admin logged In");
	}
}

