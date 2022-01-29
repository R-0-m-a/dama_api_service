/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.repository;

import com.dama.ohrringe.domain.User;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** Spring Data MongoDB repository for the {@link User} entity. */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

  String USERS_BY_LOGIN_CACHE = "usersByLogin";

  String USERS_BY_EMAIL_CACHE = "usersByEmail";

  @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
  Optional<User> findOneByEmailIgnoreCase(String email);

  @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
  Optional<User> findOneByLogin(String login);
}
