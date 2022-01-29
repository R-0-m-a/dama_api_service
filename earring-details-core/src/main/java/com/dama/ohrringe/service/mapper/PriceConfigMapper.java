/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service.mapper;

import com.dama.ohrringe.domain.PriceConfig;
import com.dama.ohrringe.dto.PriceConfigRestDto;
import org.mapstruct.Mapper;

/** The Price config mapper. */
@Mapper
public interface PriceConfigMapper {

  PriceConfig restDtoToDomain(PriceConfigRestDto restDto);

  PriceConfigRestDto domainToRestDto(PriceConfig domain);
}
