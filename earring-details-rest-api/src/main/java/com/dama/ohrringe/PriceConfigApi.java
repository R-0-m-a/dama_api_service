/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dama.ohrringe.dto.PriceConfigRestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/** The interface Price config api. */
@RequestMapping("/priceConfig")
@Tag(name = "PriceConfig", description = "Price config for earrings")
public interface PriceConfigApi {

  @Operation(summary = "Get price config", description = "Get price config for earrings")
  @GetMapping()
  @CrossOrigin
  ResponseEntity<PriceConfigRestDto> getPriceConfig();

  @Operation(
      summary = "Update price config",
      description = "Update price config. For update parameter \"id\" is required")
  @PutMapping(produces = APPLICATION_JSON_VALUE)
  @CrossOrigin
  ResponseEntity<PriceConfigRestDto> putPriceConfig(
      @RequestBody @Parameter(description = "new price config")
          PriceConfigRestDto priceConfigRestDto);
}
