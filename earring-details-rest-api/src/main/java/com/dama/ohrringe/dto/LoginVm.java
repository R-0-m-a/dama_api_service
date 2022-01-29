/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/** View Model object for storing a user's credentials. */
@Data
@Builder
@Schema(description = "View model for logging")
public class LoginVm {

  @NotNull
  @Size(min = 1, max = 50)
  @Schema(description = "user name", example = "admin")
  private String username;

  @NotNull
  @Size(min = 4, max = 100)
  @Schema(description = "password", example = "admin")
  private String password;

  @Schema(description = "remember user", example = "true")
  private boolean rememberMe;
}
