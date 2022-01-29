/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configuration for Swagger. */
@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI().info(new Info().title("Dama Api").version("0.0.1"));
  }
}
