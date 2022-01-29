/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import static com.dama.ohrringe.common.exception.ApplicationErrorStatus.ENTITY_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.config.MongoConfig;
import com.dama.ohrringe.domain.EarringDetail;
import com.dama.ohrringe.dto.EarringDetailRestDto;
import com.dama.ohrringe.security.SecurityConfig;
import com.dama.ohrringe.service.EarringDetailService;
import com.dama.ohrringe.util.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
    controllers = EarringDetailController.class,
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {SecurityConfig.class, MongoConfig.class})
    })
@ActiveProfiles("test")
class EarringDetailControllerTest {

  String baseUrl = "/earringParts";

  @Autowired private ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;

  @MockBean EarringDetailService earringDetailService;

  @Test
  void postEarringDetail() throws Exception {
    EarringDetail earringDetail = TestUtils.getEarringDetail("3qQgWX");
    Mockito.when(earringDetailService.createEarringDetail(Mockito.any())).thenReturn(earringDetail);

    mockMvc
        .perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        EarringDetailRestDto.builder().name(earringDetail.getName()).build())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.is(earringDetail.getName())))
        .andExpect(jsonPath("$.price", Matchers.is(earringDetail.getPrice())))
        .andExpect(jsonPath("$.description", Matchers.is(earringDetail.getDescription())))
        .andExpect(jsonPath("$.color", Matchers.is(earringDetail.getColor())))
        .andExpect(jsonPath("$.material", Matchers.is(earringDetail.getMaterial())))
        .andExpect(jsonPath("$.identifier", Matchers.is(earringDetail.getIdentifier())));
  }

  @Test
  void putEarringDetail() throws Exception {
    EarringDetail earringDetail = TestUtils.getEarringDetail("q9rv");
    Mockito.when(earringDetailService.createEarringDetail(Mockito.any())).thenReturn(earringDetail);

    mockMvc
        .perform(
            put(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        EarringDetailRestDto.builder().name(earringDetail.getName()).build())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.is(earringDetail.getName())))
        .andExpect(jsonPath("$.price", Matchers.is(earringDetail.getPrice())))
        .andExpect(jsonPath("$.description", Matchers.is(earringDetail.getDescription())))
        .andExpect(jsonPath("$.color", Matchers.is(earringDetail.getColor())))
        .andExpect(jsonPath("$.material", Matchers.is(earringDetail.getMaterial())))
        .andExpect(jsonPath("$.identifier", Matchers.is(earringDetail.getIdentifier())));
  }

  @Test
  void getAllEarringDetails() throws Exception {

    List<EarringDetail> earringDetails =
        List.of(
            TestUtils.getEarringDetail("1"),
            TestUtils.getEarringDetail("2"),
            TestUtils.getEarringDetail("3"));

    Mockito.when(earringDetailService.findAllEarringDetails()).thenReturn(earringDetails);

    mockMvc
        .perform(get(baseUrl))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(3)))
        .andExpect(jsonPath("$[0].name", Matchers.is(earringDetails.get(0).getName())))
        .andExpect(jsonPath("$[1].color", Matchers.is(earringDetails.get(1).getColor())))
        .andExpect(jsonPath("$[2].price", Matchers.is(earringDetails.get(2).getPrice())));
  }

  @Test
  void deleteById() throws Exception {
    mockMvc.perform(delete(baseUrl.concat("/{id}"), "1")).andExpect(status().isOk());

    String failedId = "999";
    ApplicationException exception =
        new ApplicationException(ENTITY_NOT_FOUND, "earring details", failedId);
    Mockito.doThrow(exception).when(earringDetailService).deleteById(failedId);
    mockMvc
        .perform(delete(baseUrl.concat("/{id}"), failedId))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof ApplicationException))
        .andExpect(
            result ->
                assertEquals(
                    Objects.requireNonNull(result.getResolvedException()).getMessage(),
                    exception.getMessage()));
  }

  @Test
  void getEarringDetail() throws Exception {
    String id = "YbD";
    EarringDetail earringDetail = TestUtils.getEarringDetail(id);
    Mockito.when(earringDetailService.findEarringDetailById(id)).thenReturn(earringDetail);

    String failedId = "BwR";
    ApplicationException exception =
        new ApplicationException(ENTITY_NOT_FOUND, "earring details", failedId);
    Mockito.doThrow(exception).when(earringDetailService).findEarringDetailById(failedId);

    mockMvc
        .perform(get(baseUrl.concat("/{id}"), id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.is(earringDetail.getName())))
        .andExpect(jsonPath("$.price", Matchers.is(earringDetail.getPrice())))
        .andExpect(jsonPath("$.description", Matchers.is(earringDetail.getDescription())))
        .andExpect(jsonPath("$.color", Matchers.is(earringDetail.getColor())))
        .andExpect(jsonPath("$.material", Matchers.is(earringDetail.getMaterial())))
        .andExpect(jsonPath("$.identifier", Matchers.is(earringDetail.getIdentifier())));
    mockMvc
        .perform(get(baseUrl.concat("/{id}"), failedId))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof ApplicationException))
        .andExpect(
            result ->
                assertEquals(
                    Objects.requireNonNull(result.getResolvedException()).getMessage(),
                    exception.getMessage()));
  }
}
