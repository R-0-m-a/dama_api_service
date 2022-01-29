/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.repository;

import com.dama.ohrringe.domain.PriceConfig;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("test")
class PriceConfigRepositoryTest {

  @Autowired PriceConfigRepository repository;

  @Test
  void testCreateReadDelete() {
    String id = "BF8195D7";
    // create
    PriceConfig priceConfig =
        PriceConfig.builder().id(id).deliveryPrice(493.91).premiumRate(181.37).build();
    repository.save(priceConfig);
    // read
    List<PriceConfig> priceConfigs = repository.findAll();
    Assertions.assertNotNull(priceConfigs);
    Assertions.assertEquals(1, priceConfigs.size());
    Assertions.assertEquals(id, priceConfigs.get(0).getId());
    // delete
    repository.deleteById(id);
    Assertions.assertTrue(repository.findAll().isEmpty());
  }
}
