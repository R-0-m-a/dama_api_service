/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/** The Crystal rest dto. */
@Data
@Builder
@Schema(description = "Entity for crystal")
public class CrystalRestDto {
  @Schema(description = "name crystal", example = "Cool earring")
  private String name;

  @Schema(description = "description crystal", example = "crystal for woman")
  private String description;

  @Schema(
      description = "url crystal",
      example = "https://www.crystalidea.de/swarovski-6106-pear-16mm-crystal-sahara-sw5367692.html")
  private String url;

  @Schema(
      description = "image crystal",
      example =
          "https://www.crystalidea.de/pub/media/catalog/product/cache/57b0790f49bc04062bc3e5d3b6d075fd/6/1/6106-crystal-sahara-small.jpg")
  private String image;

  @Schema(description = "price crystal", example = "22.5")
  private Double price;
}
