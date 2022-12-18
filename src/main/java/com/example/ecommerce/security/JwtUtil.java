package com.example.ecommerce.security;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil implements Serializable {

  @Value("${spring.jwt.secret-key}")
  private String secretKey;

  @Value("${spring.jwt.expiration-time}")
  private long expirationTime;


  private Key getKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(User user, Role role) {
    log.info("[ GENERATING USER TOKEN ]");
    return Jwts.builder()
        .claim("userId", user.getId())
        .claim("name", user.getFullName())
        .claim("username", user.getEmail())
        .claim("email", user.getEmail())
        .claim("role", role.getName())
        .claim("phone", user.getPhoneNumber())
        .claim("type", "Cookie")
        .setSubject(user.getEmail())
        .setId(UUID.randomUUID().toString())
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .setIssuedAt(new Date())
        .signWith(SignatureAlgorithm.HS256, getKey()).compact();
  }

  public Claims getClaims(String token) {
    boolean checkNullOrBlank = Optional.ofNullable(token).orElse("").isBlank();
    if (checkNullOrBlank) {
      throw new JwtException("Token is null.");
    }
    try {
      return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
             UnsupportedJwtException
             | IllegalArgumentException e) {
      throw new JwtException("Token not valid");
    }

  }

}
