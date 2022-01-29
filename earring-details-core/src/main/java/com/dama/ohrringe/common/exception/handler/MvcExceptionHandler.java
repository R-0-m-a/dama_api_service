/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.common.exception.handler;

import com.dama.ohrringe.common.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@Slf4j
@ControllerAdvice
public class MvcExceptionHandler implements ProblemHandling {

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<Problem> handleException(
      ApplicationException exception, NativeWebRequest request) {
    Status statusCode = exception.getApplicationErrorStatus().getStatusCode();

    log.warn(
        "MvcExceptionHandler caught an ApplicationException: {}, response code: {} ",
        exception.getMessage(),
        statusCode.getStatusCode());

    ThrowableProblem problem =
        Problem.builder().withTitle(exception.getMessage()).withStatus(statusCode).build();

    return create(exception, problem, request);
  }
}
