/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dama.ohrringe.config.MongoConfig;
import com.dama.ohrringe.dto.LoginVm;
import com.dama.ohrringe.security.SecurityConfig;
import com.dama.ohrringe.security.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
    controllers = UserJwtController.class,
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {SecurityConfig.class, MongoConfig.class})
    })
@ActiveProfiles("test")
class UserJwtControllerTest {

  @MockBean TokenProvider tokenProvider;
  @Autowired MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  static String username = "admin";
  static String password = "password";

  @Test
  @WithMockUser
  void authorize() throws Exception {
    String baseUrl = "/authenticate";
    LoginVm loginVm = LoginVm.builder().username(username).password(password).build();
    String jwt = "0f7E7ff";
    Mockito.when(tokenProvider.createToken(Mockito.any())).thenReturn(jwt);

    mockMvc
        .perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginVm)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id_token", Matchers.is(jwt)));
  }

  @TestConfiguration
  static class SecurityConfig {
    @Primary
    @Bean
    public UserDetailsService userDetailsService() {
      UserDetails user =
          User.withDefaultPasswordEncoder()
              .username(username)
              .password(password)
              .roles("USER")
              .build();
      return new InMemoryUserDetailsManager(user);
    }
  }
}
