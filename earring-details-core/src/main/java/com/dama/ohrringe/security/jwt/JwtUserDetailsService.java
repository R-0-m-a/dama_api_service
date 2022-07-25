/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.security.jwt;

import com.dama.ohrringe.common.exception.ApplicationErrorStatus;
import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.domain.User;
import com.dama.ohrringe.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/** Authenticate a user from the database. */
@Slf4j
@AllArgsConstructor
@Component("userDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(final String login) {
    log.debug("Authenticating {}", login);

    String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
    Optional<User> optionalUser = userRepository.findOneByLogin(lowercaseLogin);
    return optionalUser
        .map(this::createSpringSecurityUser)
        .orElseThrow(
            () ->
                new ApplicationException(ApplicationErrorStatus.USER_NOT_FOUND, lowercaseLogin));
  }

  private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user) {
    List<GrantedAuthority> grantedAuthorities =
        user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(
        user.getLogin(), user.getPassword(), grantedAuthorities);
  }
}
