/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import com.dama.ohrringe.model.CrystalArtikel;
import com.dama.ohrringe.model.CrystalModel;
import com.dama.ohrringe.model.CrystalResponse;
import com.dama.ohrringe.util.TestUtils;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class CrystalServiceTest {
  @InjectMocks CrystalService service;

  @Mock RestTemplate restTemplate;

  @Test
  void searchCrystalByIdentifier() throws IOException {
    CrystalResponse crystalResponse = TestUtils.getCrystalResponse();
    Mockito.when(
            restTemplate.getForObject(
                Mockito.anyString(),
                ArgumentMatchers.eq(CrystalResponse.class),
                Mockito.anyString()))
        .thenReturn(crystalResponse);

    Optional<CrystalModel> actual = service.searchCrystalByIdentifier("T0vuZPcA");
    Assertions.assertTrue(actual.isPresent());
    CrystalModel actualCrystalModel = actual.get();
    CrystalModel expectedCrystalModel = crystalResponse.getIndexes().get(0).getItems().get(0);
    Assertions.assertEquals(expectedCrystalModel, actualCrystalModel);

    Mockito.when(
            restTemplate.getForObject(
                Mockito.anyString(),
                ArgumentMatchers.eq(CrystalResponse.class),
                Mockito.anyString()))
        .thenReturn(null);
    actual = service.searchCrystalByIdentifier("4fFhL");
    Assertions.assertTrue(actual.isEmpty());

    CrystalResponse crystalResWithOutCrystalModel =
        CrystalResponse.builder().indexes(List.of(CrystalArtikel.builder().build())).build();
    Mockito.when(
            restTemplate.getForObject(
                Mockito.anyString(),
                ArgumentMatchers.eq(CrystalResponse.class),
                Mockito.anyString()))
        .thenReturn(crystalResWithOutCrystalModel);
    actual = service.searchCrystalByIdentifier("Cws6");
    Assertions.assertTrue(actual.isEmpty());
  }
}
