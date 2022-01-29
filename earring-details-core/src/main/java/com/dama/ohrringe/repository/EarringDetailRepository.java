/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.repository;

import com.dama.ohrringe.domain.EarringDetail;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** Spring Data MongoDB repository for the {@link EarringDetail} entity. */
@Repository
public interface EarringDetailRepository extends MongoRepository<EarringDetail, String> {
  Optional<EarringDetail> findOneByName(String name);
}
