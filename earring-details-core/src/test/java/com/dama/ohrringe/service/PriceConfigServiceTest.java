/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import com.dama.ohrringe.domain.PriceConfig;
import com.dama.ohrringe.repository.PriceConfigRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceConfigServiceTest {
  @InjectMocks PriceConfigService service;
  @Mock
  PriceConfigRepository priceConfigRepository;

  @Test
  void createPriceConfig() {
    PriceConfig priceConfig = PriceConfig.builder()
                                   .deliveryPrice(375.70)
                                   .premiumRate(65.96)
                                   .build();

    Mockito.when(priceConfigRepository.save(priceConfig)).thenReturn(priceConfig);
    PriceConfig actual = service.createPriceConfig(priceConfig);
    Mockito.verify(priceConfigRepository).save(priceConfig);
    Assertions.assertNotNull(actual);
    Assertions.assertEquals(priceConfig, actual);
  }

  @Test
  void findPriceConfig() {
    PriceConfig priceConfig = PriceConfig.builder()
                                         .deliveryPrice(567.93)
                                         .premiumRate(779.62)
                                         .build();
    Mockito.when(priceConfigRepository.findAll()).thenReturn(List.of(priceConfig));
    PriceConfig actual = service.findPriceConfig();
    Mockito.verify(priceConfigRepository).findAll();
    Assertions.assertNotNull(actual);
    Assertions.assertEquals(priceConfig, actual);
  }

  @Test
  void getPremiumRate() {
    PriceConfig priceConfig = PriceConfig.builder()
                                         .deliveryPrice(773.28)
                                         .premiumRate(493.99)
                                         .build();

    Mockito.when(priceConfigRepository.findAll()).thenReturn(List.of(priceConfig));
    double actual = service.getPremiumRate();
    Mockito.verify(priceConfigRepository).findAll();
    Assertions.assertEquals(priceConfig.getPremiumRate(), actual);
  }

  @Test
  void getDeliveryPrice() {
    PriceConfig priceConfig = PriceConfig.builder()
                                         .deliveryPrice(942.32)
                                         .premiumRate(218.94)
                                         .build();

    Mockito.when(priceConfigRepository.findAll()).thenReturn(List.of(priceConfig));
    double actual = service.getDeliveryPrice();
    Mockito.verify(priceConfigRepository).findAll();
    Assertions.assertEquals(priceConfig.getDeliveryPrice(), actual);
  }
}