/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import static com.dama.ohrringe.common.exception.ApplicationErrorStatus.EARRING_DETAIL_CAN_NOT_DELETE;
import static com.dama.ohrringe.common.exception.ApplicationErrorStatus.ENTITY_NOT_FOUND;

import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.domain.Earring;
import com.dama.ohrringe.domain.EarringDetail;
import com.dama.ohrringe.repository.EarringDetailRepository;
import com.dama.ohrringe.repository.EarringRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/** The Earring detail service. */
@Slf4j
@AllArgsConstructor
@Service
public class EarringDetailService {

  private EarringDetailRepository earringDetailRepository;
  private EarringRepository earringRepository;

  public EarringDetail createEarringDetail(EarringDetail newEarringDetail) {
    return earringDetailRepository.save(newEarringDetail);
  }

  public List<EarringDetail> findAllEarringDetails() {
    return earringDetailRepository.findAll();
  }

  /**
   * Delete an earring detail by id.
   *
   * @param id the id
   */
  public void deleteById(String id) {
    if (!earringDetailRepository.existsById(id)) {
      log.error("Earring detail with id {} not found", id);
      throw new ApplicationException(ENTITY_NOT_FOUND, "earring details", id);
    }

    Optional<List<Earring>> optionalEarrings = earringRepository.findByEarringDetails_Id(id);
    if (optionalEarrings.isPresent()) {
      List<Earring> earringsMew = optionalEarrings.get();
      if (!CollectionUtils.isEmpty(earringsMew)) {
        String errMessage = String.format("Earring detail is using in earrings: %n");
        List<String> nameEarrings =
            earringsMew.stream().map(Earring::getName).collect(Collectors.toList());
        for (String name : nameEarrings) {
          errMessage = errMessage.concat(String.format("%s %n", name));
        }
        log.error("Earring detail can not delete with id {}, the detail is using in earrings.", id);
        throw new ApplicationException(errMessage, EARRING_DETAIL_CAN_NOT_DELETE);
      }
    }
    earringDetailRepository.deleteById(id);
  }

  /**
   * Find earring detail by id.
   *
   * @param id the id
   * @return {@link EarringDetail} the earring detail
   */
  public EarringDetail findEarringDetailById(String id) {
    Optional<EarringDetail> earringDetailById = earringDetailRepository.findById(id);
    if (earringDetailById.isEmpty()) {
      log.error("Earring details with id {} not found", id);
      throw new ApplicationException(ENTITY_NOT_FOUND, "earring details", id);
    }
    return earringDetailById.get();
  }
}
