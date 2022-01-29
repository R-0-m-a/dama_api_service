/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.domain;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** A price config. */
@Data
@SuperBuilder
@Document("price_config")
@NoArgsConstructor
public class PriceConfig extends AbstractAuditingEntity {
  @Id private String id;

  @NotNull private double premiumRate;
  @NotNull private double deliveryPrice;
}
