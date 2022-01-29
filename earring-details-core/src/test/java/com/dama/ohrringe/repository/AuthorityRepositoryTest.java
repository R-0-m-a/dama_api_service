/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.repository;

import com.dama.ohrringe.domain.Authority;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("test")
class AuthorityRepositoryTest {
  @Autowired AuthorityRepository repository;

  @Test
  void testCreateReadDelete() {
    String name = "ROLE_USER";
    // create
    Authority authority = Authority.builder().name(name).build();
    repository.save(authority);
    // read
    List<Authority> authorities = repository.findAll();
    Assertions.assertNotNull(authorities);
    Assertions.assertEquals(1, authorities.size());
    Assertions.assertEquals(name, authorities.get(0).getName());
    // delete
    repository.deleteById(name);
    Assertions.assertTrue(repository.findAll().isEmpty());
  }
}
