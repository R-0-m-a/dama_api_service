/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** JSON Web Tokens configuration. */
public class JwtConfigurer
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final TokenProvider tokenProvider;

  public JwtConfigurer(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public void configure(HttpSecurity http) {
    JwtFilter customFilter = new JwtFilter(tokenProvider);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
