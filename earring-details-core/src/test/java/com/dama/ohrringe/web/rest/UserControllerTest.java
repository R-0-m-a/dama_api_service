/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dama.ohrringe.config.MongoConfig;
import com.dama.ohrringe.domain.Authority;
import com.dama.ohrringe.domain.User;
import com.dama.ohrringe.dto.ManagedUserVm;
import com.dama.ohrringe.security.SecurityConfig;
import com.dama.ohrringe.security.UserRole;
import com.dama.ohrringe.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.Set;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
    controllers = UserController.class,
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {SecurityConfig.class, MongoConfig.class})
    })
@ActiveProfiles("test")
class UserControllerTest {

  @Autowired MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean UserService userService;

  @Test
  void isAuthenticated() throws Exception {
    String uri = "/authenticate";

    mockMvc.perform(get(uri)).andExpect(status().isUnauthorized());

    String user = "testUser";
    mockMvc
        .perform(
            get(uri)
                .with(
                    request -> {
                      request.setRemoteUser(user);
                      return request;
                    }))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.is(user)));
  }

  @Test
  void getAccount() throws Exception {
    String url = "/account";
    String userId = "M6fb";

    Mockito.when(userService.getUserWithAuthorities())
        .thenReturn(Optional.ofNullable(getUser(userId)));
    mockMvc.perform(get(url)).andExpect(status().isOk());

    Mockito.when(userService.getUserWithAuthorities()).thenReturn(Optional.empty());
    mockMvc.perform(get(url)).andExpect(status().isUnauthorized());
  }

  @Test
  void registerAccount() throws Exception {
    String url = "/register";
    String userId = "AuzCf";
    User user = getUser(userId);
    Mockito.when(userService.registerUser(Mockito.any(), Mockito.anyString())).thenReturn(user);
    mockMvc
        .perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ManagedUserVm.builder().build())))
        .andExpect(status().isCreated());
  }

  private User getUser(String userId) {
    Authority adminAuthority = Authority.builder().name(UserRole.ADMIN.getRoleName()).build();
    Authority userAuthority = Authority.builder().name(UserRole.USER.getRoleName()).build();

    return User.builder()
        .firstName("firstName".concat(userId))
        .lastName("lastName".concat(userId))
        .email("email".concat(userId))
        .login("login".concat(userId))
        .password("password".concat(userId))
        .authorities(Set.of(adminAuthority, userAuthority))
        .build();
  }
}
