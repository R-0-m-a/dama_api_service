/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.domain.Authority;
import com.dama.ohrringe.domain.User;
import com.dama.ohrringe.repository.UserRepository;
import com.dama.ohrringe.security.UserRole;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @InjectMocks UserService service;
  @Mock UserRepository userRepository;
  @Mock PasswordEncoder passwordEncoder;

  @Test
  void getUserWithAuthorities() {
    GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_AUTH");
    String login = "user";
    TestingAuthenticationToken authentication =
        new TestingAuthenticationToken(login, "bar", List.of(authority));
    authentication.setAuthenticated(true);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    User user = User.builder().login(login).lastName("yPI").firstName("1Hw0").build();
    Mockito.when(userRepository.findOneByLogin(login)).thenReturn(Optional.ofNullable(user));
    Optional<User> actual = service.getUserWithAuthorities();
    Mockito.verify(userRepository).findOneByLogin(login);
    Assertions.assertFalse(actual.isEmpty());
    Assertions.assertEquals(user, actual.get());
  }

  @Test
  void registerUser() {

    User user =
        User.builder()
            .login("5cfKQ50")
            .lastName("9V63")
            .firstName("WrN6LD")
            .email("VT65Rnn3")
            .build();
    String password = "1si59";

    Mockito.when(userRepository.findOneByLogin(user.getLogin())).thenReturn(Optional.of(user));
    Assertions.assertThrows(
        ApplicationException.class,
        () -> service.registerUser(user, password),
        "ApplicationException error was expected");

    Mockito.when(userRepository.findOneByLogin(user.getLogin())).thenReturn(Optional.empty());
    Mockito.when(userRepository.findOneByEmailIgnoreCase(user.getEmail()))
        .thenReturn(Optional.of(user));
    Assertions.assertThrows(
        ApplicationException.class,
        () -> service.registerUser(user, password),
        "ApplicationException error was expected");

    Mockito.when(userRepository.findOneByLogin(user.getLogin())).thenReturn(Optional.empty());
    Mockito.when(userRepository.findOneByEmailIgnoreCase(user.getEmail()))
        .thenReturn(Optional.empty());
    String encryptedPassword = "7zOSy7H";
    Mockito.when(passwordEncoder.encode(password)).thenReturn(encryptedPassword);
    User userWithEncryptedPassword = SerializationUtils.clone(user);
    userWithEncryptedPassword.setPassword(encryptedPassword);
    userWithEncryptedPassword.setAuthorities(
        Set.of(Authority.builder().name(UserRole.USER.getRoleName()).build()));
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(userWithEncryptedPassword);
    User actual = service.registerUser(user, password);
    Assertions.assertNotNull(actual);
    Assertions.assertEquals(userWithEncryptedPassword, actual);
  }
}
