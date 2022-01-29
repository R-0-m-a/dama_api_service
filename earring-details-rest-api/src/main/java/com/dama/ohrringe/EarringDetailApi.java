/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.dama.ohrringe.dto.EarringDetailRestDto;
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

/** The interface Earring detail api. */
@RequestMapping("/earringParts")
@Tag(name = "Earring Details", description = "Working with details for earrings")
public interface EarringDetailApi {

  @Operation(summary = "Get all details", description = "Get all details for earrings")
  @GetMapping()
  @CrossOrigin
  ResponseEntity<List<EarringDetailRestDto>> getAllEarringDetails();

  @Operation(summary = "Get detail by id", description = "Get detail for earrings by id")
  @GetMapping("/{id}")
  @CrossOrigin
  ResponseEntity<EarringDetailRestDto> getEarringDetail(
      @Parameter(description = "id detail", example = "60a819945ad3b32b9cf31c64")
          @PathVariable("id")
          String id);

  @Operation(summary = "Add detail", description = "Add detail for earrings.")
  @PostMapping(produces = APPLICATION_JSON_VALUE)
  @CrossOrigin
  ResponseEntity<EarringDetailRestDto> postEarringDetail(
      @RequestBody @Parameter(description = "new detail")
          EarringDetailRestDto earringDetailRestDto);

  @Operation(
      summary = "Update detail",
      description = "Update detail for earrings. For update parameter \"id\" is required")
  @PutMapping(produces = APPLICATION_JSON_VALUE)
  @CrossOrigin
  ResponseEntity<EarringDetailRestDto> putEarringDetail(
      @RequestBody @Parameter(description = "new detail")
          EarringDetailRestDto earringDetailRestDto);

  @Operation(summary = "Delete by id", description = "Delete earring detail by id")
  @DeleteMapping("/{id}")
  @CrossOrigin
  ResponseEntity<Void> deleteById(
      @Parameter(description = "id detail", example = "60a819945ad3b32b9cf31c64")
          @PathVariable("id")
          String id);
}
