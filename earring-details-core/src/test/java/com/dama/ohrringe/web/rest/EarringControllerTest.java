/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dama.ohrringe.config.MongoConfig;
import com.dama.ohrringe.domain.Earring;
import com.dama.ohrringe.dto.EarringRestDto;
import com.dama.ohrringe.security.SecurityConfig;
import com.dama.ohrringe.service.EarringService;
import com.dama.ohrringe.service.PriceConfigService;
import com.dama.ohrringe.util.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
    controllers = EarringController.class,
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {SecurityConfig.class, MongoConfig.class})
    })
@ActiveProfiles("test")
class EarringControllerTest {
  String baseUrl = "/earrings";
  @Autowired private ObjectMapper objectMapper;
  @Autowired MockMvc mockMvc;

  @MockBean EarringService earringService;

  @MockBean PriceConfigService priceConfigService;

  double premiumRate = 25;
  double deliveryPrice = 5;

  @BeforeEach
  void initMockPriceConfig() {
    Mockito.when(priceConfigService.getPremiumRate()).thenReturn(premiumRate);
    Mockito.when(priceConfigService.getDeliveryPrice()).thenReturn(deliveryPrice);
  }

  @Test
  void getAllEarrings() throws Exception {
    List<Earring> earrings =
        List.of(TestUtils.getEarring("1"), TestUtils.getEarring("2"), TestUtils.getEarring("3"));
    Mockito.when(earringService.findAllEarrings()).thenReturn(earrings);

    mockMvc
        .perform(get(baseUrl))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", Matchers.hasSize(3)))
        .andExpect(jsonPath("$[0].name", Matchers.is(earrings.get(0).getName())))
        .andExpect(jsonPath("$[1].id", Matchers.is(earrings.get(1).getId())))
        .andExpect(jsonPath("$[2].description", Matchers.is(earrings.get(2).getDescription())));
  }

  @Test
  void getEarring() throws Exception {
    String id = "5";

    Earring earring = TestUtils.getEarring(id, true);

    Mockito.when(earringService.findEarringById(id)).thenReturn(Optional.ofNullable(earring));

    assert earring != null;
    double detailPrice = earring.getEarringDetails().get(0).getPrice();
    double premium = getPremium(detailPrice);
    mockMvc
        .perform(get(baseUrl.concat("/{id}"), id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.is(earring.getName())))
        .andExpect(jsonPath("$.description", Matchers.is(earring.getDescription())))
        .andExpect(jsonPath("$.imageUrl", Matchers.is(earring.getImageUrl())))
        .andExpect(jsonPath("$.deliveryPrice", Matchers.is(deliveryPrice)))
        .andExpect(jsonPath("$.premium", Matchers.is(premium)))
        .andExpect(jsonPath("$.totalPrice", Matchers.is(detailPrice + deliveryPrice + premium)));

    Mockito.when(earringService.findEarringById(Mockito.anyString())).thenReturn(Optional.empty());
    mockMvc.perform(get(baseUrl.concat("/{id}"), "r836S")).andExpect(status().isNotFound());
  }

  @Test
  void deleteById() throws Exception {
    this.mockMvc.perform(delete(baseUrl.concat("/{id}"), "1")).andExpect(status().isOk());
  }

  @Test
  void postEarring() throws Exception {
    Earring earring = TestUtils.getEarring("7", true);
    double detailPrice = earring.getEarringDetails().get(0).getPrice();
    double premium = getPremium(detailPrice);
    Mockito.when(earringService.createEarring(Mockito.any())).thenReturn(earring);
    mockMvc
        .perform(
            post(baseUrl)
                .content(objectMapper.writeValueAsString(EarringRestDto.builder().build()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", Matchers.is(earring.getName())))
        .andExpect(jsonPath("$.description", Matchers.is(earring.getDescription())))
        .andExpect(jsonPath("$.imageUrl", Matchers.is(earring.getImageUrl())))
        .andExpect(jsonPath("$.deliveryPrice", Matchers.is(deliveryPrice)))
        .andExpect(jsonPath("$.premium", Matchers.is(premium)))
        .andExpect(jsonPath("$.totalPrice", Matchers.is(detailPrice + deliveryPrice + premium)));
  }

  @Test
  void putEarring() throws Exception {
    Earring earring = TestUtils.getEarring("8", true);
    double detailPrice = earring.getEarringDetails().get(0).getPrice();
    double premium = getPremium(detailPrice);
    Mockito.when(earringService.createEarring(Mockito.any())).thenReturn(earring);
    mockMvc
        .perform(
            put(baseUrl)
                .content(objectMapper.writeValueAsString(EarringRestDto.builder().build()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.is(earring.getName())))
        .andExpect(jsonPath("$.description", Matchers.is(earring.getDescription())))
        .andExpect(jsonPath("$.imageUrl", Matchers.is(earring.getImageUrl())))
        .andExpect(jsonPath("$.deliveryPrice", Matchers.is(deliveryPrice)))
        .andExpect(jsonPath("$.premium", Matchers.is(premium)))
        .andExpect(jsonPath("$.totalPrice", Matchers.is(detailPrice + deliveryPrice + premium)));
  }

  private double getPremium(double detailPrice) {
    return detailPrice * premiumRate / 100;
  }
}
