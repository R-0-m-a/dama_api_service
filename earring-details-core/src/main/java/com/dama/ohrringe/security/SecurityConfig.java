/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.security;

import com.dama.ohrringe.security.jwt.JwtFilter;
import com.dama.ohrringe.security.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

/** The Security config. */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenProvider tokenProvider;
  private final CorsFilter corsFilter;

  public SecurityConfig(TokenProvider tokenProvider, CorsFilter corsFilter) {
    this.tokenProvider = tokenProvider;
    this.corsFilter = corsFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    JwtFilter customFilter = new JwtFilter(tokenProvider);
    http.httpBasic()
        .disable()
        .csrf()
        .disable()
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/earringParts/**")
        .hasAuthority(UserRole.ADMIN.getRoleName())
        .antMatchers("/earrings/**", "/register", "/authenticate")
        .permitAll()
        .and()
        .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
