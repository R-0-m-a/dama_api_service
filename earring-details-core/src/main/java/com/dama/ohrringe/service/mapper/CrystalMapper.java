/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service.mapper;

import com.dama.ohrringe.dto.CrystalRestDto;
import com.dama.ohrringe.model.CrystalModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/** The Crystal mapper. */
@Mapper
public interface CrystalMapper {
  @Mapping(target = "price", source = "price", qualifiedByName = "priceRestDto")
  CrystalRestDto modelToRestDto(CrystalModel model);

  /**
   * Gets price.
   *
   * @param priceString the price string
   * @return the price {@link Double}
   */
  @Named("priceRestDto")
  static Double getPrice(String priceString) {
    Double result = null;
    if (StringUtils.isNoneBlank(priceString)) {
      Pattern pattern = Pattern.compile("(data-price-amount=\")([\\d.]+)\"");
      Matcher matcher = pattern.matcher(priceString);

      if (matcher.find()) {
        String price = matcher.group(2);
        if (StringUtils.isNoneBlank(price)) {
          result = Double.valueOf(price);
        }
      }
    }
    return result;
  }
}
