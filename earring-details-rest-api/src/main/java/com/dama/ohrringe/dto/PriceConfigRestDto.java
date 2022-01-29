/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/** The Price config rest dto. */
@Data
@Builder
@Schema(description = "Configuration for price")
public class PriceConfigRestDto {
  @Schema(description = "id", example = "60a819945ad3b32b9cf31c64")
  private String id;

  @Schema(description = "premium rate", example = "2.5")
  private double premiumRate;

  @Schema(description = "delivery Price", example = "5")
  private double deliveryPrice;
}
