/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import static com.dama.ohrringe.common.exception.ApplicationErrorStatus.ENTITY_NOT_FOUND;

import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.domain.Earring;
import com.dama.ohrringe.repository.EarringRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/** The Earring service. */
@Slf4j
@AllArgsConstructor
@Service
public class EarringService {

  private EarringRepository earringRepository;

  public Earring createEarring(Earring newEarring) {
    return earringRepository.save(newEarring);
  }

  public List<Earring> findAllEarrings() {
    return earringRepository.findAll();
  }

  /**
   * Delete the earring service by id.
   *
   * @param id the id
   */
  public void deleteById(String id) {
    if (!earringRepository.existsById(id)) {
      log.error("Earring with id {} not found", id);
      throw new ApplicationException(ENTITY_NOT_FOUND, "earring ", id);
    }
    earringRepository.deleteById(id);
  }

  /**
   * Find earring by id.
   *
   * @param id the id
   * @return {@link Optional} the earring
   */
  public Optional<Earring> findEarringById(String id) {
    if (!earringRepository.existsById(id)) {
      log.error("Earring with id {} not found", id);
      throw new ApplicationException(ENTITY_NOT_FOUND, "earring", id);
    }
    return earringRepository.findById(id);
  }
}
