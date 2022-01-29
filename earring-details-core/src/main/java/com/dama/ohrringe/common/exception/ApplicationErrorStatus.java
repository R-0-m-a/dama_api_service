/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

/** The enum Application error status. */
@Getter
@AllArgsConstructor
public enum ApplicationErrorStatus {
  GENERIC_ERROR("Unexpected exception"),
  ENTITY_ALREADY_EXIST(Status.CONFLICT, "%s with  id %s has already existed in database"),
  EARRING_DETAIL_CAN_NOT_DELETE(Status.CONFLICT, "Earring detail can not delete"),
  USER_ALREADY_EXIST(Status.CONFLICT, "%s with login %s has already existed in database"),
  USER_IS_NOT_AUTHENTICATED(Status.UNAUTHORIZED, "User is not authenticated"),
  ENTITY_NOT_FOUND(Status.NOT_FOUND, "%s with id %s not found in database"),
  CRYSTAL_NOT_FOUND(Status.NOT_FOUND, "%s with identifier %s not found in server"),
  EMAIL_ALREADY_EXIST(Status.CONFLICT, "%s with email %s has already existed in database");

  final Status statusCode;
  final String errorMessage;

  ApplicationErrorStatus(String errorMessage) {
    this(Status.BAD_REQUEST, errorMessage);
  }
}
