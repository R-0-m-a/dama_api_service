/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.web.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dama.ohrringe.common.exception.ApplicationException;
import com.dama.ohrringe.config.MongoConfig;
import com.dama.ohrringe.model.CrystalArtikel;
import com.dama.ohrringe.model.CrystalModel;
import com.dama.ohrringe.model.CrystalResponse;
import com.dama.ohrringe.security.SecurityConfig;
import com.dama.ohrringe.service.CrystalService;
import com.dama.ohrringe.util.TestUtils;
import java.util.List;
import java.util.Optional;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WithMockUser
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(
    controllers = CrystalController.class,
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.ASSIGNABLE_TYPE,
          classes = {SecurityConfig.class, MongoConfig.class})
    })
@ActiveProfiles("test")
class CrystalControllerTest {

  @MockBean CrystalService crystalService;
  @Autowired MockMvc mockMvc;

  @Test
  void searchCrystalByIdentifier() throws Exception {
    CrystalResponse crystalResponse = TestUtils.getCrystalResponse();
    List<CrystalArtikel> indices = crystalResponse.getIndexes();
    CrystalArtikel crystalArtikel = indices.get(0);
    List<CrystalModel> crystalModels = crystalArtikel.getItems();
    CrystalModel crystalModel = crystalModels.get(0);
    Mockito.when(crystalService.searchCrystalByIdentifier(Mockito.anyString()))
        .thenReturn(Optional.of(crystalModel));

    mockMvc
        .perform(get("/crystal/{identifier}", "b1F980"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.is(crystalModel.getName())))
        .andExpect(jsonPath("$.description", Matchers.is(crystalModel.getDescription())))
        .andExpect(jsonPath("$.image", Matchers.is(crystalModel.getImage())))
        .andExpect(jsonPath("$.url", Matchers.is(crystalModel.getUrl())));

    Mockito.when(crystalService.searchCrystalByIdentifier(Mockito.anyString()))
        .thenReturn(Optional.empty());
    mockMvc
        .perform(get("/crystal/{identifier}", "b1F980"))
        .andExpect(status().isNotFound())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof ApplicationException));
  }
}
