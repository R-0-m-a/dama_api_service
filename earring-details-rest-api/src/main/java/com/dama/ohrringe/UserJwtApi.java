/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe;

import com.dama.ohrringe.dto.JwtToken;
import com.dama.ohrringe.dto.LoginVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/** The interface User api. */
@Tag(name = "User", description = "Working with user")
public interface UserJwtApi {

  /**
   * Authorize response entity.
   *
   * @param loginVm {@link LoginVm}
   * @return the response entity
   */
  @Operation(summary = "Authorizing user", description = "Authorizing user.")
  @PostMapping("/authenticate")
  @CrossOrigin
  ResponseEntity<JwtToken> authorize(
      @Valid
          @RequestBody
          @Parameter(description = "view Model object for storing a user's credentials.")
          LoginVm loginVm);
}
