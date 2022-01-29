/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import static com.dama.ohrringe.common.exception.ApplicationErrorStatus.CRYSTAL_NOT_FOUND;

import com.dama.ohrringe.CrystalApi;
import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.dto.CrystalRestDto;
import com.dama.ohrringe.model.CrystalModel;
import com.dama.ohrringe.service.CrystalService;
import com.dama.ohrringe.service.mapper.CrystalMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Crystal controller. */
@RestController
@Slf4j
public class CrystalController implements CrystalApi {
  CrystalService crystalService;
  CrystalMapper mapper = Mappers.getMapper(CrystalMapper.class);

  @Autowired
  public CrystalController(CrystalService crystalService) {
    this.crystalService = crystalService;
  }

  @Override
  public ResponseEntity<CrystalRestDto> searchCrystalByIdentifier(String identifier) {
    log.info("Received request to searching crystal by identifier {}", identifier);
    Optional<CrystalModel> crystalModelOptional =
        crystalService.searchCrystalByIdentifier(identifier);

    if (crystalModelOptional.isPresent()) {
      CrystalRestDto crystalRestDto = mapper.modelToRestDto(crystalModelOptional.get());
      log.info(
          "Received request to searching crystal by identifier {} is processed successfully",
          identifier);
      return ResponseEntity.ok(crystalRestDto);
    } else {
      log.warn("Searching crystal by identifier {} was not found", identifier);
      throw new ApplicationException(CRYSTAL_NOT_FOUND, "crystal", identifier);
    }
  }
}
