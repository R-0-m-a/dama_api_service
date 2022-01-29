/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.dto;

import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** View Model extending the UserRestDto, which is meant to be used in the user management UI. */
@Data
@SuperBuilder
@NoArgsConstructor
public class ManagedUserVm extends UserRestDto {

  public static final int PASSWORD_MIN_LENGTH = 4;

  public static final int PASSWORD_MAX_LENGTH = 100;

  @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
  private String password;
}
