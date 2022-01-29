/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Constants for Spring Security authorities. */
@AllArgsConstructor
@Getter
public enum UserRole {
  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER"),
  ANONYMOUS("ROLE_ANONYMOUS");

  final String roleName;
}
