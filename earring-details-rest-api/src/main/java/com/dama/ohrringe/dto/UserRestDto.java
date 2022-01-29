/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/** The User rest dto. */
@Data
@SuperBuilder
@NoArgsConstructor
@Schema(description = "Entity for user")
public class UserRestDto {
  @Schema(description = "user id", example = "user-1")
  private String id;
  @Schema(description = "login", example = "admin")
  private String login;
  @Schema(description = "first name", example = "admin")
  private String firstName;
  @Schema(description = "last name", example = "Administrator")
  private String lastName;
  @Schema(description = "email", example = "admin@localhost")
  private String email;
  @Schema(description = "image url")
  private String imageUrl;
  @Schema(description = "user is activated", example = "false")
  private boolean activated = false;
  @Schema(description = "language", example = "en")
  private String langKey;
  @Schema(description = "user created by ", example = "system")
  private String createdBy;
  @Schema(description = "created date", example = "2022-06-15T13:42:20.762Z")
  private Instant createdDate;
  @Schema(description = "last modified by", example = "2022-06-15T13:42:20.762Z")
  private String lastModifiedBy;
  @Schema(description = "last modified date", example = "2022-06-15T13:42:20.779Z")
  private Instant lastModifiedDate;
  @Schema(
      description = "authorities",
      example = "[\n" + "        \"ROLE_USER\",\n" + "        \"ROLE_ADMIN\"\n" + "    ]")
  private Set<String> authorities;
}
