/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service.mapper;

import com.dama.ohrringe.domain.Authority;
import com.dama.ohrringe.domain.User;
import com.dama.ohrringe.dto.UserRestDto;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/** The interface User mapper. */
@Mapper
public interface UserMapper {

  /**
   * Gets authorities domain.
   *
   * @param authorities {@link Set}<{@link String}>
   * @return authorities {@link Set}<{@link Authority}>
   */
  @Named("authoritiesDomain")
  static Set<Authority> authoritiesDomain(Set<String> authorities) {
    Set<Authority> result = new HashSet<>();

    if (authorities != null) {
      result =
          authorities.stream()
              .map(s -> Authority.builder().name(s).build())
              .collect(Collectors.toSet());
    }
    return result;
  }

  @Named("authoritiesRestDto")
  static Set<String> authoritiesRestDto(Set<Authority> authorities) {
    return authorities.stream().map(Authority::getName).collect(Collectors.toSet());
  }

  @Mapping(target = "authorities", source = "authorities", qualifiedByName = "authoritiesDomain")
  User restDtoToDomain(UserRestDto restDto);

  @Mapping(target = "authorities", source = "authorities", qualifiedByName = "authoritiesRestDto")
  UserRestDto domainToRestDto(User domain);
}
