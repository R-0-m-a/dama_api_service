/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service.mapper;

import com.dama.ohrringe.domain.EarringDetail;
import com.dama.ohrringe.dto.EarringDetailRestDto;
import java.util.List;
import org.mapstruct.Mapper;

/** The Earring detail mapper. */
@Mapper
public interface EarringDetailMapper {

  EarringDetail restDtoToDomain(EarringDetailRestDto restDto);

  List<EarringDetail> restDtoToDomain(List<EarringDetailRestDto> restDto);

  EarringDetailRestDto domainToRestDto(EarringDetail domain);

  List<EarringDetailRestDto> domainToRestDto(List<EarringDetail> domain);
}
