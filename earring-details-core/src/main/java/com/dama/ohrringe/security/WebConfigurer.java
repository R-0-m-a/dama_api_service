/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.security;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/** Configuration of web application. */
@Slf4j
@Configuration
public class WebConfigurer {
  private static final List<String> DEFAULT_PERMIT_ALL = Collections.singletonList("*");
  private static final long DEFAULT_MAX_AGE = 1800L;

  /**
   * Cors filter.
   *
   * @return {@link CorsFilter} the cors filter
   */
  @Bean
  public CorsFilter corsFilter() {
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
