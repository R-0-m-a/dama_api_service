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

/** The Crystal response. */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrystalResponse {
  private float totalItems;
  List<CrystalArtikel> indexes;
}
