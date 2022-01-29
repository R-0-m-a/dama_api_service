/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.common.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

  private final ApplicationErrorStatus applicationErrorStatus;

  public ApplicationException(String message, ApplicationErrorStatus applicationErrorStatus) {
    super(message);
    this.applicationErrorStatus = applicationErrorStatus;
  }

  public ApplicationException(
      ApplicationErrorStatus applicationErrorStatus, Object... placeholders) {
    super(String.format(applicationErrorStatus.errorMessage, placeholders));
    this.applicationErrorStatus = applicationErrorStatus;
  }

  public ApplicationException(
      Throwable cause, ApplicationErrorStatus applicationErrorStatus, Object... placeholders) {
    super(String.format(applicationErrorStatus.errorMessage, placeholders), cause);
    this.applicationErrorStatus = applicationErrorStatus;
  }

  public ApplicationException(Throwable cause, ApplicationErrorStatus applicationErrorStatus) {
    super(applicationErrorStatus.errorMessage, cause);
    this.applicationErrorStatus = applicationErrorStatus;
  }
}
