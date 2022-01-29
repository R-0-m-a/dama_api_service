/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import com.dama.ohrringe.UserJwtApi;
import com.dama.ohrringe.dto.JwtToken;
import com.dama.ohrringe.dto.LoginVm;
import com.dama.ohrringe.security.jwt.JwtFilter;
import com.dama.ohrringe.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

/** Controller to authenticate users. */
@RestController
@Slf4j
@AllArgsConstructor
public class UserJwtController implements UserJwtApi {
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final TokenProvider tokenProvider;

  @Override
  public ResponseEntity<JwtToken> authorize(LoginVm loginVm) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginVm.getUsername(), loginVm.getPassword());

    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.createToken(authentication);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    return new ResponseEntity<>(new JwtToken(jwt), httpHeaders, HttpStatus.OK);
  }
}
