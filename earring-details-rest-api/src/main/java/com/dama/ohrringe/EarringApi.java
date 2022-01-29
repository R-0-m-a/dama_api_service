/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dama.ohrringe.dto.EarringRestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/** The interface Earring api. */
@RequestMapping("/earrings")
@Tag(name = "Earrings", description = "Working with earrings")
public interface EarringApi {

  @Operation(summary = "Get all earrings", description = "Get all earrings")
  @GetMapping()
  @CrossOrigin
  ResponseEntity<List<EarringRestDto>> getAllEarrings();

  @Operation(summary = "Get earring by id", description = "Get earring by id")
  @GetMapping("/{id}")
  @CrossOrigin
  ResponseEntity<EarringRestDto> getEarring(
      @Parameter(description = "id earring", example = "60a819945ad3b32b9cf31c64")
          @PathVariable("id")
          String id);

  @Operation(summary = "Add earring", description = "Add earring.")
  @PostMapping(produces = APPLICATION_JSON_VALUE)
  @CrossOrigin
  ResponseEntity<EarringRestDto> postEarring(
      @RequestBody @Parameter(description = "new earring") EarringRestDto earringRestDto);

  @Operation(
      summary = "Update earring",
      description = "Update earring. For update parameter \"id\" is required")
  @PutMapping(produces = APPLICATION_JSON_VALUE)
  @CrossOrigin
  ResponseEntity<EarringRestDto> putEarring(
      @RequestBody @Parameter(description = "new earring") EarringRestDto earringRestDto);

  @Operation(summary = "Delete by id", description = "Delete earring by id")
  @DeleteMapping("/{id}")
  @CrossOrigin
  ResponseEntity<Void> deleteById(
      @Parameter(description = "id earring", example = "60a819945ad3b32b9cf31c64")
          @PathVariable("id")
          String id);
}
