package com.abc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.request.AuthenticationRequest;
import com.abc.request.RegisterRequest;
import com.abc.response.AuthenticationResponse;
import com.abc.service.AuthenticateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationControler {

	private final AuthenticateService authenticateService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {
		AuthenticationResponse authenticationResponse = authenticateService.register(registerRequest);
		return ResponseEntity.ok(authenticationResponse);

	}
	
	@PostMapping("authenticate")
	public ResponseEntity<AuthenticationResponse> athenticate(@RequestBody AuthenticationRequest authenticationRequest){
		return ResponseEntity.ok(authenticateService.authenticate(authenticationRequest));
	}
}
