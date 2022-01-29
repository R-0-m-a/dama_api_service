/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.security;

import com.dama.ohrringe.domain.User;
import com.dama.ohrringe.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/** Authenticate a user from the database. */
@Slf4j
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public DomainUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(final String login) {
    log.debug("Authenticating {}", login);

    String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
    Optional<User> optionalUser = userRepository.findOneByLogin(lowercaseLogin);
    return optionalUser
        .map(this::createSpringSecurityUser)
        .orElseThrow(
            () ->
                new UsernameNotFoundException(
                    "User " + lowercaseLogin + " was not found in the database"));
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
