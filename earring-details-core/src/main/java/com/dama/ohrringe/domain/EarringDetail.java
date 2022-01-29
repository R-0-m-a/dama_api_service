/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** An earring detail. */
@Data
@SuperBuilder
@Document("earring_details")
@NoArgsConstructor
public class EarringDetail extends AbstractAuditingEntity {

  @Id private String id;
  @NonNull @Indexed private String name;
  private String description;
  private String identifier;
  private String material;

  @Field("image_url")
  private String imageUrl;

  private String color;
  private double price;
}
