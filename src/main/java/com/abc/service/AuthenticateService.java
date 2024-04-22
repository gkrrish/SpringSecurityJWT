package com.abc.service;

import org.springframework.stereotype.Service;

import com.abc.jwt.JwtService;
import com.abc.models.Users;
import com.abc.repository.UserRepository;
import com.abc.request.RegisterRequest;
import com.abc.response.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

	private final UserRepository userRepository;
	private final JwtService jwtService;

	public AuthenticationResponse register(RegisterRequest registerRequest) {
		var user=Users.builder()
				.firstName(registerRequest.getFirstName())
				.lastName(registerRequest.getLastName())
				.email(registerRequest.getEmail())
				.password(registerRequest.getPassword())
				.role(registerRequest.getRole())
				.build();
				
		Users savedUser = userRepository.save(user);
		String jwtToken = jwtService.generateToken(savedUser);
		return AuthenticationResponse.builder().accessToken(jwtToken).build();
	}

}
