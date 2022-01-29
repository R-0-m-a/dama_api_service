/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.domain;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** An authority (a security role) used by Spring Security. */
@Document(collection = "authority")
@Data
@Builder
public class Authority implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull
  @Size(max = 50)
  @Id
  private String name;
}
