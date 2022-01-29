/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.repository;

import com.dama.ohrringe.domain.User;
import com.dama.ohrringe.util.TestUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.CollectionUtils;

@DataMongoTest
@ActiveProfiles("test")
class UserRepositoryTest {

  @Autowired UserRepository repository;

  @AfterEach
  public void cleanUp() {
    repository.deleteAll();
  }

  @Test
  void testCreateReadDelete() {
    String id = "Pdnj3";
    // create
    init(List.of(TestUtils.getUser(id)));
    // read
    List<User> users = repository.findAll();
    Assertions.assertNotNull(users);
    Assertions.assertEquals(1, users.size());
    Assertions.assertEquals(id, users.get(0).getId());
    // delete
    repository.deleteById(id);
    Assertions.assertTrue(repository.findAll().isEmpty());
  }

  @Test
  void findOneByEmailIgnoreCase() {
    User user = TestUtils.getUser("rZcnc1");
    init(List.of(user, TestUtils.getUser("Hzs6")));
    Optional<User> userOptional = repository.findOneByEmailIgnoreCase(user.getEmail());
    Assertions.assertTrue(userOptional.isPresent());
    Assertions.assertEquals(user.getEmail(), userOptional.get().getEmail());
  }

  @Test
  void findOneByLogin() {
    User user = TestUtils.getUser("xXIkHO");
    init(List.of(user, TestUtils.getUser("t72fjk")));
    Optional<User> userOptional = repository.findOneByLogin(user.getLogin());
    Assertions.assertTrue(userOptional.isPresent());
    Assertions.assertEquals(user.getLogin(), userOptional.get().getLogin());
  }

  private void init(List<User> users) {
    if (!CollectionUtils.isEmpty(users)) {
      repository.saveAll(users);
    }
  }
}
