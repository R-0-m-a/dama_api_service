/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe;

import com.dama.ohrringe.dto.ManagedUserVm;
import com.dama.ohrringe.dto.UserRestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/** The interface User api. */
@Tag(name = "User", description = "Working with user")
public interface UserApi {

  /**
   * Сheck if the user is authenticated, and return its login.
   *
   * @param request the request
   * @return the response entity
   */
  @Operation(
      summary = "Сheck if the user is authenticated",
      description = "Сheck if the user is authenticated, and return its login.")
  @GetMapping("/authenticate")
  @CrossOrigin
  ResponseEntity<String> isAuthenticated(HttpServletRequest request);

  /**
   * Gets account.
   *
   * @return the account
   */
  @Operation(summary = "Gets account", description = "Gets account.")
  @GetMapping("/account")
  @CrossOrigin
  ResponseEntity<UserRestDto> getAccount();

  /**
   * Register the user.
   *
   * @param managedUserVm the managed user vm
   * @return the response entity
   */
  @Operation(summary = "Register the user", description = "Register the user.")
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  @CrossOrigin
  ResponseEntity<Void> registerAccount(
      @Valid @RequestBody @Parameter(description = "the managed user View Model")
          ManagedUserVm managedUserVm);
}
