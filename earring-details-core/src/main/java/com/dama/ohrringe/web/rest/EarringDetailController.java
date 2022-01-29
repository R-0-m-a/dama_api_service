/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import com.dama.ohrringe.EarringDetailApi;
import com.dama.ohrringe.domain.EarringDetail;
import com.dama.ohrringe.dto.EarringDetailRestDto;
import com.dama.ohrringe.service.EarringDetailService;
import com.dama.ohrringe.service.mapper.EarringDetailMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Earring detail controller. */
@RestController
@Slf4j
public class EarringDetailController implements EarringDetailApi {
  EarringDetailService earringDetailService;
  EarringDetailMapper mapper = Mappers.getMapper(EarringDetailMapper.class);

  @Autowired
  public EarringDetailController(EarringDetailService earringDetailService) {
    this.earringDetailService = earringDetailService;
  }

  @Override
  public ResponseEntity<EarringDetailRestDto> postEarringDetail(
      EarringDetailRestDto earringDetailRestDto) {
    log.info("Received request to add earring detail {}", earringDetailRestDto);
    EarringDetail earringDetail = mapper.restDtoToDomain(earringDetailRestDto);
    EarringDetail newEarringDetail = earringDetailService.createEarringDetail(earringDetail);
    EarringDetailRestDto restDto = mapper.domainToRestDto(newEarringDetail);
    log.info("Received request to add earring detail {} is processed successfully", restDto);
    return ResponseEntity.ok(restDto);
  }

  @Override
  public ResponseEntity<EarringDetailRestDto> putEarringDetail(
      EarringDetailRestDto earringDetailRestDto) {
    log.info("Received request to update earring detail {}", earringDetailRestDto);
    EarringDetail earringDetail = mapper.restDtoToDomain(earringDetailRestDto);
    EarringDetail newEarringDetail = earringDetailService.createEarringDetail(earringDetail);
    EarringDetailRestDto restDto = mapper.domainToRestDto(newEarringDetail);
    log.info("Received request to update earring detail {} is processed successfully", restDto);
    return ResponseEntity.ok(restDto);
  }

  @Override
  public ResponseEntity<List<EarringDetailRestDto>> getAllEarringDetails() {
    log.info("Received request to get earring details");
    List<EarringDetail> allEarringDetails = earringDetailService.findAllEarringDetails();
    List<EarringDetailRestDto> earringDetailsRestDto = mapper.domainToRestDto(allEarringDetails);
    log.info("Received request to get earring details is processed successfully");
    return ResponseEntity.ok(earringDetailsRestDto);
  }

  /**
   * Delete the earring details by id.
   *
   * @param id {@link String}
   * @return {@link ResponseEntity}
   */
  public ResponseEntity<Void> deleteById(String id) {
    log.info("Received request to delete earring detail by id {}", id);
    earringDetailService.deleteById(id);
    log.info("Received request to delete earring detail by id {} is processed successfully", id);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<EarringDetailRestDto> getEarringDetail(String id) {
    log.info("Received request to get earring detail by id {}", id);
    EarringDetail earringDetail = earringDetailService.findEarringDetailById(id);
    EarringDetailRestDto earringDetailRestDto = mapper.domainToRestDto(earringDetail);
    log.info("Received request to get earring detail by id {} is processed successfully", id);
    return ResponseEntity.ok(earringDetailRestDto);
  }
}
