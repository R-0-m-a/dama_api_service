/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/** The Jwt token. */
@Data
@AllArgsConstructor
@ToString
public class JwtToken {
  @JsonProperty("id_token")
  private String idToken;
}
