package com.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.dto.JwtAuthenticationResponse;
import com.authentication.dto.RefreshTokenRequest;
import com.authentication.dto.SignInRequest;
import com.authentication.dto.SignUpRequest;
import com.authentication.entity.User;
import com.authentication.service.AuthenticationService;

@RestController
@RequestMapping("api/v1/auth")

public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;

	@PostMapping("/user-signup")
	public ResponseEntity<User> signUp(@RequestBody final SignUpRequest signUpRequest) {
		return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
	}

	@PostMapping("/admin-signup")
	public ResponseEntity<User> adminSignUp(@RequestBody final SignUpRequest signUpRequest) {
		return ResponseEntity.ok(authenticationService.adminSignUp(signUpRequest));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody final SignInRequest signInRequest) {
		if (!StringUtils.hasText(signInRequest.getEmail())) {
			return ResponseEntity.badRequest().body("Enter email id");
		}
		if (!StringUtils.hasText(signInRequest.getPassword())) {
			return ResponseEntity.badRequest().body("Enter Password");
		}
		try {
			return ResponseEntity.ok(authenticationService.signIn(signInRequest));
		} catch (IllegalArgumentException | UsernameNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody final RefreshTokenRequest refreshTokenRequest) {
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
	}

	@ExceptionHandler({ IllegalArgumentException.class, UsernameNotFoundException.class })
	public ResponseEntity<String> handleAuthenticationException(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

}
