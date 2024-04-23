package com.abc.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abc.jwt.JwtService;
import com.abc.models.Users;
import com.abc.repository.UserRepository;
import com.abc.request.AuthenticationRequest;
import com.abc.request.RegisterRequest;
import com.abc.response.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateService {

	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	
	public AuthenticationResponse register(RegisterRequest registerRequest) {
		var user=Users.builder()
				.firstName(registerRequest.getFirstName())
				.lastName(registerRequest.getLastName())
				.email(registerRequest.getEmail())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.role(registerRequest.getRole())
				.build();
				
		Users savedUser = userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().accessToken(jwtToken).build();
	}
	
	

	public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
		
		/**
		 * Authentication Process:
		 * =======================
		 * 
		 * First Step:
		 * -----------
		 * This step involves validating the incoming request to ensure that both the username and password are correct. 
		 * It verifies whether the user is present in the database.
		 * The authentication provider used in this step is the DaoAuthenticationProvider, which is injected because we are utilizing a database for user authentication.
		 * The authentication process is carried out by authenticating using the authentication manager, which has been injected with this AuthenticationProvider.
		 * 
		 * Second Step:
		 * ------------
		 * In this step, we verify whether the provided username and password are correct by utilizing the UsernamePasswordAuthenticationToken.
		 * Additionally, we ensure that the user exists in the database.
		 * Upon successful verification, a token is generated.
		 * Finally, the generated token is returned.
		 */

		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
								authenticationRequest.getEmail(), 
								authenticationRequest.getPassword())
				);
		
		var availableUser=userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
		String jwtToken = jwtService.generateToken(availableUser);
		return AuthenticationResponse.builder().accessToken(jwtToken).build();
	}

}
