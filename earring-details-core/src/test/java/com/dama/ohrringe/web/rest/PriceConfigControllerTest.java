/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dama.ohrringe.config.MongoConfig;
import com.dama.ohrringe.domain.PriceConfig;
import com.dama.ohrringe.dto.PriceConfigRestDto;
import com.dama.ohrringe.security.SecurityConfig;
import com.dama.ohrringe.service.PriceConfigService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    controllers = PriceConfigController.class,
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {SecurityConfig.class, MongoConfig.class})
    })
@ActiveProfiles("test")
class PriceConfigControllerTest {

  @MockBean PriceConfigService priceConfigService;
  @Autowired MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  String baseUrl = "/priceConfig";

  @Test
  void getPriceConfig() throws Exception {
    PriceConfig priceConfig =
        PriceConfig.builder().deliveryPrice(404.50).premiumRate(961.82).build();
    Mockito.when(priceConfigService.findPriceConfig()).thenReturn(priceConfig);
    mockMvc
        .perform(get(baseUrl))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.deliveryPrice", Matchers.is(priceConfig.getDeliveryPrice())))
        .andExpect(jsonPath("$.premiumRate", Matchers.is(priceConfig.getPremiumRate())));
  }

  @Test
  void putPriceConfig() throws Exception {
    PriceConfig priceConfig =
        PriceConfig.builder().deliveryPrice(561.55).premiumRate(917.99).build();
    Mockito.when(priceConfigService.createPriceConfig(Mockito.any())).thenReturn(priceConfig);

    mockMvc
        .perform(
            put(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PriceConfigRestDto.builder().build())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.deliveryPrice", Matchers.is(priceConfig.getDeliveryPrice())))
        .andExpect(jsonPath("$.premiumRate", Matchers.is(priceConfig.getPremiumRate())));
  }
}
