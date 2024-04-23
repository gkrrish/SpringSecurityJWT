package com.abc.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abc.enums.Role;
import static com.abc.enums.Permission.*;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
	
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request -> request
						.requestMatchers("/users/v1/authentication/*")
						.permitAll()// White-listing the specified URL pattern
						
						.requestMatchers("/users/v1/management/**").hasAnyRole(Role.ADMIN.name(),Role.MEMBER.name())
						
						.requestMatchers(GET, "/users/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MEMBER_READ.name())
                        .requestMatchers(POST, "/users/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MEMBER_CREATE.name())
						
						.anyRequest()// All other URLs require authentication
						.authenticated())
				
			.sessionManagement(session->session.sessionCreationPolicy(STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

}






















/**
 * Configuring Security:
 * =====================
 * 
 * Request Matchers Configuration:
 * -------------------------------
 * 
 * Request Matcher "/users/v1/authentication/*":
 * ----------------------------------------------
 * This line specifies a request matcher for URLs starting with "/users/v1/authentication/".
 * Requests to these URLs are permitted without authentication, effectively whitelisting them.
 * 
 * Permit All:
 * -----------
 * The permitAll() method is used to whitelist the specific URL pattern defined above.
 * It allows unrestricted access to the URLs matching "/users/v1/authentication/*".
 * 
 * Any Request:
 * ------------
 * The anyRequest() method is used to define the authorization rule for any remaining URLs.
 * These URLs are not explicitly whitelisted and therefore require authentication.
 * 
 * Authentication Requirement:
 * ---------------------------
 * This configuration ensures that all requests to URLs not matching "/users/v1/authentication/*" 
 * will require authentication before they can be accessed.
 */
