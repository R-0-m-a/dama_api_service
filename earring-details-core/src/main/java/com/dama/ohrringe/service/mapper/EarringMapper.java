/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.service.mapper;

import com.dama.ohrringe.domain.Earring;
import com.dama.ohrringe.dto.EarringRestDto;
import java.util.List;
import org.mapstruct.Mapper;

/** The Earring mapper. */
@Mapper
public interface EarringMapper {

  Earring restDtoToDomain(EarringRestDto restDto);

  List<Earring> restDtoToDomain(List<EarringRestDto> restDto);

  EarringRestDto domainToRestDto(Earring domain);

  List<EarringRestDto> domainToRestDto(List<Earring> domain);
}
