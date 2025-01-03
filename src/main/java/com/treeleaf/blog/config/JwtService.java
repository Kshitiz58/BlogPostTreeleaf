package com.treeleaf.blog.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

	private static final String SECRET = "OZMd+NI8c2xensVNT+KTQV4w3RR0+mu+Ivu0Z/Syyxm7nI9jKJr8yWvGrBgEZqTcxLiysPbGAA8noRfFZtymRg==";
	private static final long VALIDITY = TimeUnit.DAYS.toMillis(1);

	public String generateToken(CustomUserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("userId", userDetails.getId());
		claims.put("userType", userDetails.getUserType());

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(getSigningKey())
				.compact();
	}

	public String extractUsername(String jwt) {
		return getClaims(jwt).getSubject();
	}

	public Long extractId(String jwt) {
		return getClaims(jwt).get("userId", Long.class);
	}

	public String extractUserType(String jwt) {
		return getClaims(jwt).get("userType", String.class);
	}

	public boolean isTokenValid(String jwt, UserDetails userDetails) {
		final String username = extractUsername(jwt);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
	}

	private boolean isTokenExpired(String jwt) {
		return getClaims(jwt).getExpiration().before(new Date());
	}

	private Claims getClaims(String jwt) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(jwt)
				.getBody();
	}

	private SecretKey getSigningKey() {
		byte[] keyBytes = java.util.Base64.getDecoder().decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
