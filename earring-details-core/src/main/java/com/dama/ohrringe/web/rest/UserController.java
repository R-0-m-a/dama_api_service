/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import com.dama.ohrringe.UserApi;
import com.dama.ohrringe.common.exception.ApplicationErrorStatus;
import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.dto.ManagedUserVm;
import com.dama.ohrringe.dto.UserRestDto;
import com.dama.ohrringe.service.UserService;
import com.dama.ohrringe.service.mapper.UserMapper;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The User controller. */
@RestController
@Slf4j
public class UserController implements UserApi {

  private final UserService userService;
  UserMapper mapper = Mappers.getMapper(UserMapper.class);

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Override
  public ResponseEntity<String> isAuthenticated(HttpServletRequest request) {
    log.debug("REST request to check if the current user is authenticated");
    String remoteUser = request.getRemoteUser();
    if (remoteUser == null) {
      throw new ApplicationException(ApplicationErrorStatus.USER_IS_NOT_AUTHENTICATED);
    }
    return ResponseEntity.ok(remoteUser);
  }

  @Override
  public ResponseEntity<UserRestDto> getAccount() {
    UserRestDto userRestDto =
        userService
            .getUserWithAuthorities()
            .map(user -> mapper.domainToRestDto(user))
            .orElseThrow(
                () -> new ApplicationException(ApplicationErrorStatus.USER_IS_NOT_AUTHENTICATED));
    return ResponseEntity.ok(userRestDto);
  }

  @Override
  public ResponseEntity<Void> registerAccount(ManagedUserVm managedUserVm) {
    userService.registerUser(mapper.restDtoToDomain(managedUserVm), managedUserVm.getPassword());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
