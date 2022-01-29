/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import com.dama.ohrringe.domain.PriceConfig;
import com.dama.ohrringe.repository.PriceConfigRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** The Price config service. */
@Slf4j
@AllArgsConstructor
@Service
public class PriceConfigService {

  private PriceConfigRepository priceConfigRepository;

  public PriceConfig createPriceConfig(PriceConfig priceConfig) {
    return priceConfigRepository.save(priceConfig);
  }

  public PriceConfig findPriceConfig() {
    return priceConfigRepository.findAll().get(0);
  }

  public double getPremiumRate() {
    return findPriceConfig().getPremiumRate();
  }

  public double getDeliveryPrice() {
    return findPriceConfig().getDeliveryPrice();
  }
}
