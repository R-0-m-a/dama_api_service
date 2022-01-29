/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.repository;

import com.dama.ohrringe.domain.Earring;
import com.dama.ohrringe.domain.EarringDetail;
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
class EarringRepositoryTest {

  @Autowired EarringRepository earringRepository;

  @AfterEach
  public void cleanUp() {
    earringRepository.deleteAll();
  }

  @Test
  void testCreateReadDelete() {
    String id = "dhO2y";
    // create
    init(List.of(TestUtils.getEarring(id)));
    // read
    List<Earring> earrings = earringRepository.findAll();
    Assertions.assertNotNull(earrings);
    Assertions.assertEquals(1, earrings.size());
    Assertions.assertEquals(id, earrings.get(0).getId());
    // delete
    earringRepository.deleteById(id);
    Assertions.assertTrue(earringRepository.findAll().isEmpty());
  }

  @Test
  void findOneByName() {
    Earring earring = TestUtils.getEarring("C3392Q");
    init(List.of(earring));
    String name = earring.getName();
    Optional<Earring> actual = earringRepository.findOneByName(name);
    Assertions.assertFalse(actual.isEmpty());
    Assertions.assertEquals(earring, actual.get());
  }

  @Test
  void findByEarringDetails_Id() {
    List<EarringDetail> detailList =
        List.of(
            TestUtils.getEarringDetail("1"),
            TestUtils.getEarringDetail("2"),
            TestUtils.getEarringDetail("3"));

    List<Earring> earrings =
        List.of(
            TestUtils.getEarring("1", detailList),
            TestUtils.getEarring("2"),
            TestUtils.getEarring("3", detailList));

    init(earrings);

    Optional<List<Earring>> optionalEarrings = earringRepository.findByEarringDetails_Id("2");
    Assertions.assertTrue(optionalEarrings.isPresent());
    Assertions.assertEquals(2, optionalEarrings.get().size());

    optionalEarrings = earringRepository.findByEarringDetails_Id("6");
    Assertions.assertTrue(optionalEarrings.isPresent());
    Assertions.assertEquals(0, optionalEarrings.get().size());
  }

  private void init(List<Earring> earrings) {
    if (!CollectionUtils.isEmpty(earrings)) {
      earringRepository.saveAll(earrings);
    }
  }
}
