/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import com.dama.ohrringe.PriceConfigApi;
import com.dama.ohrringe.domain.PriceConfig;
import com.dama.ohrringe.dto.PriceConfigRestDto;
import com.dama.ohrringe.service.PriceConfigService;
import com.dama.ohrringe.service.mapper.PriceConfigMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Price config controller. */
@RestController
@Slf4j
public class PriceConfigController implements PriceConfigApi {

  PriceConfigService priceConfigService;
  PriceConfigMapper mapper = Mappers.getMapper(PriceConfigMapper.class);

  @Autowired
  public PriceConfigController(PriceConfigService priceConfigService) {
    this.priceConfigService = priceConfigService;
  }

  @Override
  public ResponseEntity<PriceConfigRestDto> getPriceConfig() {
    log.info("Received request to get price configuration");
    PriceConfig priceConfig = priceConfigService.findPriceConfig();
    PriceConfigRestDto priceConfigRestDto = mapper.domainToRestDto(priceConfig);
    log.info("Received request to get price configuration is processed successfully");
    return ResponseEntity.ok(priceConfigRestDto);
  }

  @Override
  public ResponseEntity<PriceConfigRestDto> putPriceConfig(PriceConfigRestDto priceConfigRestDto) {
    log.info("Received request to update price configuration {}", priceConfigRestDto);
    PriceConfig priceConfig =
        priceConfigService.createPriceConfig(mapper.restDtoToDomain(priceConfigRestDto));
    PriceConfigRestDto restDto = mapper.domainToRestDto(priceConfig);
    log.info(
        "Received request to update price configuration{} is processed successfully", priceConfig);
    return ResponseEntity.ok(restDto);
  }
}
