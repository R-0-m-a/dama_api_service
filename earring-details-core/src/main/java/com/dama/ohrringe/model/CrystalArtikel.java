/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** The Crystal artikel. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrystalArtikel {
  private String identifier;
  private String title;
  private float position;
  List<CrystalModel> items;
  private float totalItems;
  private boolean isShowTotals;
}
