package com.abc.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abc.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		
			final String authenticationHeader=request.getHeader("Authorization");
			String jwt;
			String email;
			
			
			if (authenticationHeader == null || authenticationHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}

			jwt = authenticationHeader.substring(7);
			email = jwtService.extractUserName(jwt);
			
			if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
				
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
			}
			filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(
			@NonNull HttpServletRequest request) throws ServletException {
		
		return request.getServletPath().contains("/users/v1/authentication/");
	}
	
}


/**
 * Token Authentication Process:
 * =============================
 * 
 * Verification Process:
 * ---------------------
 * 
 * Verify if it isn't a whitelisted path, and if it is, don't take any action.
 * 
 * Verify whether the request has an authorization header containing a bearer token.
 * 
 * Extract the user from the token.
 * 
 * If the user is present and no authentication object is found in the security context:
 * 
 *   - Verify whether the user is present in the database.
 *   
 *   - Verify whether the token is valid.
 *   
 *   - If the token is valid, set it to the security context header.
 */
