/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/** The Earring detail rest dto. */
@Data
@Builder
@Schema(description = "Entity detail for earring")
public class EarringDetailRestDto {
  @Schema(description = "id detail", example = "60a819945ad3b32b9cf31c64")
  private String id;

  @Schema(description = "name detail", example = "Verschluss")
  private String name;

  @Schema(description = "description detail", example = "Beschreibung")
  private String description;

  @Schema(description = "identifier for detail", example = "SW5085541")
  private String identifier;

  @Schema(description = "material detail", example = "Messing")
  private String material;

  @Schema(description = "name file for image", example = "Verschluss_1.jpg")
  private String imageUrl;

  @Schema(description = "color detail", example = "zitronengelb")
  private String color;

  @Schema(description = "price detail", example = "5")
  private double price;
}
