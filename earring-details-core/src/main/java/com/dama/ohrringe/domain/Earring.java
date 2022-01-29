/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.domain;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/** An earring. */
@Data
@SuperBuilder
@NoArgsConstructor
@Document("earrings")
public class Earring extends AbstractAuditingEntity {

  @Id private String id;
  private String name;
  private String description;

  @Field("image_url")
  private String imageUrl;

  @DBRef @NotNull private List<EarringDetail> earringDetails;
}
