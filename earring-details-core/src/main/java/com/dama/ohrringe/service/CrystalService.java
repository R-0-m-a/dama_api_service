/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service;

import com.dama.ohrringe.model.CrystalArtikel;
import com.dama.ohrringe.model.CrystalModel;
import com.dama.ohrringe.model.CrystalResponse;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

/** The Crystal service. */
@Slf4j
@Service
@AllArgsConstructor
public class CrystalService {
  RestTemplate restTemplate;

  /**
   * Search crystal by identifier.
   *
   * @param identifier {@link String}
   * @return {@link Optional}<{@link List} of Objects>
   */
  public Optional<CrystalModel> searchCrystalByIdentifier(String identifier) {
    String url = "https://www.crystalidea.de/searchautocomplete/ajax/suggest/?q={identifier}";
    CrystalResponse response = restTemplate.getForObject(url, CrystalResponse.class, identifier);
    if (response != null) {
      List<CrystalArtikel> crystalArtikels = response.getIndexes();
      if (!CollectionUtils.isEmpty(crystalArtikels)) {
        List<CrystalModel> crystalModels = crystalArtikels.get(0).getItems();
        if (!CollectionUtils.isEmpty(crystalModels)) {
          return Optional.ofNullable(crystalModels.get(0));
        }
      }
    }
    log.info("Crystal with identifier {} not found in server", identifier);
    return Optional.empty();
  }
}
