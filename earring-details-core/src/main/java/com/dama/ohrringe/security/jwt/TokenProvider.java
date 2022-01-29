/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/** The Token provider. */
@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
  private final Key key;
  private final JwtParser jwtParser;

  /**
   * Instantiates a new Token provider.
   *
   * @param secret the secret
   */
  public TokenProvider(@Value("${jwtSecret}") String secret) {
    byte[] keyBytes;
    log.debug("Using a Base64-encoded JWT secret key");
    keyBytes = secret.getBytes(StandardCharsets.UTF_8);
    key = Keys.hmacShaKeyFor(keyBytes);
    jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
  }

  /**
   * Create token string.
   *
   * @param authentication {@link Authentication}
   * @return the authentication token
   */
  public String createToken(Authentication authentication) {
    String authorities =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
    Date validity =
        Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());

    return Jwts.builder()
        .setSubject(authentication.getName())
        .claim(AUTHORITIES_KEY, authorities)
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(validity)
        .compact();
  }

  /**
   * Gets authentication.
   *
   * @param token the authentication token
   * @return {@link Authentication} authentication
   */
  public Authentication getAuthentication(String token) {
    Claims claims = jwtParser.parseClaimsJws(token).getBody();

    Collection<? extends GrantedAuthority> authorities =
        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    User principal = new User(claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }

  /**
   * Validating an authentication token.
   *
   * @param authToken the authentication token
   * @return the boolean
   */
  public boolean validateToken(String authToken) {
    try {
      jwtParser.parseClaimsJws(authToken);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      log.info("Invalid JWT token.");
      log.trace("Invalid JWT token trace.", e);
    }
    return false;
  }
}
