/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The Crystal model. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrystalModel {
  private String name;
  private String url;
  private String sku;
  private String description;
  private String image;
  private String price;
  private String rating;
  @JsonProperty("stock_status")
  private float stockStatus;

}
