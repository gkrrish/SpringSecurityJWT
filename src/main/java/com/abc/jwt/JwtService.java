package com.abc.jwt;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET = "DR6T789U5678tydfYU675RT5r678iuyfd6t78976546789iuhgfcdr567890iouohjgfdr67890iojkhgfrt6789ioujkhgrt6789iujk";

	public String generateToken(UserDetails user) {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("authorities", populateAuthorities(user.getAuthorities()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 86400000))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();				
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> authoritiesSet = new HashSet<>();
		for (GrantedAuthority authority : authorities) {
			authoritiesSet.add(authority.getAuthority());
		}
		//MEMBER, ADMIN
		return String.join(",", authoritiesSet);
	}
}
