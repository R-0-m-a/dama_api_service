/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.repository;

import com.dama.ohrringe.domain.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** Spring Data MongoDB repository for the {@link Authority} entity. */
@Repository
public interface AuthorityRepository extends MongoRepository<Authority, String> {}
