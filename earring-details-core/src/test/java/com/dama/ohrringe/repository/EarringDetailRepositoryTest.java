/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.repository;

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
class EarringDetailRepositoryTest {

  @Autowired EarringDetailRepository repository;

  @AfterEach
  public void cleanUp() {
    repository.deleteAll();
  }

  @Test
  void testCreateReadDelete() {
    String id = "YiOSRfM";
    // create
    init(List.of(TestUtils.getEarringDetail(id)));
    // read
    List<EarringDetail> earringDetails = repository.findAll();
    Assertions.assertNotNull(earringDetails);
    Assertions.assertEquals(1, earringDetails.size());
    Assertions.assertEquals(id, earringDetails.get(0).getId());
    // delete
    repository.deleteById(id);
    Assertions.assertTrue(repository.findAll().isEmpty());
  }

  @Test
  void findOneByName() {
    EarringDetail earringDetail = TestUtils.getEarringDetail("rdiflJ5");
    init(List.of(earringDetail));
    String name = earringDetail.getName();
    Optional<EarringDetail> actual = repository.findOneByName(name);
    Assertions.assertFalse(actual.isEmpty());
    Assertions.assertEquals(earringDetail, actual.get());
  }

  private void init(List<EarringDetail> earringDetails) {
    if (!CollectionUtils.isEmpty(earringDetails)) {
      repository.saveAll(earringDetails);
    }
  }
}
