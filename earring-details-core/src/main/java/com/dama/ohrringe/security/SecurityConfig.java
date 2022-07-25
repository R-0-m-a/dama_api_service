/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.security;

import com.dama.ohrringe.security.jwt.JwtFilter;
import com.dama.ohrringe.security.jwt.TokenProvider;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/** The Security config. */
@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final TokenProvider tokenProvider;
  private static final List<String> DEFAULT_PERMIT_ALL = Collections.singletonList("*");
  private static final long DEFAULT_MAX_AGE = 1800L;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    JwtFilter customFilter = new JwtFilter(tokenProvider);
    http
        .httpBasic().disable()
        .csrf().disable()
        .addFilterBefore(getCorsFilter(), UsernamePasswordAuthenticationFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/earringParts/**").hasAuthority(UserRole.ADMIN.getRoleName())
        .antMatchers("/priceConfig/**").hasAuthority(UserRole.ADMIN.getRoleName())
        .antMatchers("/earrings/**", "/register", "/authenticate").permitAll()
        .and()
        .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Cors filter.
   *
   * @return {@link CorsFilter} the cors filter
   */
  public CorsFilter getCorsFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowedOrigins(DEFAULT_PERMIT_ALL);
    corsConfig.setAllowedMethods(DEFAULT_PERMIT_ALL);
    corsConfig.setAllowedHeaders(DEFAULT_PERMIT_ALL);
    corsConfig.setMaxAge(DEFAULT_MAX_AGE);

    log.debug("Registering CORS filter");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    source.registerCorsConfiguration("/earringApi/**", corsConfig);
    source.registerCorsConfiguration("/management/**", corsConfig);
    source.registerCorsConfiguration("/v2/api-docs", corsConfig);
    source.registerCorsConfiguration("/v3/api-docs", corsConfig);

    return new CorsFilter(source);
  }
}
