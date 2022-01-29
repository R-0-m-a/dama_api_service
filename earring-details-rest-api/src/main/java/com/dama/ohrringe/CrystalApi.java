/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe;

import com.dama.ohrringe.dto.CrystalRestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/** The interface crystal api. */
@RequestMapping("/crystal")
@Tag(name = "Crystal", description = "Crystal details")
public interface CrystalApi {

  @Operation(summary = "Search crystal", description = "Search crystal")
  @GetMapping("/{identifier}")
  @CrossOrigin
  ResponseEntity<CrystalRestDto> searchCrystalByIdentifier(
      @Parameter(description = "identifier", example = "SW5367692") @PathVariable("identifier")
          String identifier);
}
