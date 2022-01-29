/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.util;

import com.dama.ohrringe.domain.Earring;
import com.dama.ohrringe.domain.EarringDetail;
import com.dama.ohrringe.domain.User;
import com.dama.ohrringe.model.CrystalResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.RandomUtils;

/** The utils for testing. */
public final class TestUtils {

  /**
   * Gets earring.
   *
   * @param id the id
   * @return the earring
   */
  public static Earring getEarring(String id) {
    return getEarring(id, false);
  }

  /**
   * Gets earring.
   *
   * @param id the id
   * @param earringDetails the earring details
   * @return the earring
   */
  public static Earring getEarring(String id, List<EarringDetail> earringDetails) {
    Earring earring = getEarring(id, false);
    earring.setEarringDetails(earringDetails);
    return earring;
  }

  /**
   * Gets earring.
   *
   * @param id the id
   * @param hasEarringDetails has earring details
   * @return the earring
   */
  public static Earring getEarring(String id, boolean hasEarringDetails) {
    Earring earring =
        Earring.builder()
            .id(id)
            .name("name".concat(id))
            .imageUrl("image".concat(id))
            .description("description".concat(id))
            .build();

    if (hasEarringDetails) {
      earring.setEarringDetails(List.of(getEarringDetail("details".concat(id))));
    }
    return earring;
  }


  /**
   * Gets earring detail.
   *
   * @param id the id
   * @return the earring detail
   */
  public static EarringDetail getEarringDetail(String id) {
    return EarringDetail.builder()
        .id(id)
        .name("name".concat(id))
        .description("description".concat(id))
        .color("color".concat(id))
        .imageUrl("imageUrl".concat(id))
        .material("material".concat(id))
        .identifier("identifier".concat(id))
        .price(RandomUtils.nextDouble(0.0, 1000.0))
        .build();
  }

  /**
   * Gets crystal response.
   *
   * @return the crystal response
   * @throws IOException the io exception
   */
  public static CrystalResponse getCrystalResponse() throws IOException {
    String filePath = "src/test/resources/responses/crystal_rs.json";
    return getCrystalResponse(filePath);
  }

  /**
   * Gets crystal response.
   *
   * @param filePath the file path
   * @return the crystal response
   * @throws IOException the io exception
   */
  public static CrystalResponse getCrystalResponse(String filePath) throws IOException {
    ObjectMapper objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    File file = new File(filePath);
    return objectMapper.readValue(file, CrystalResponse.class);
  }

  /**
   * Gets user.
   *
   * @param id the id
   * @return the user
   */
  public static User getUser(String id) {
    return User.builder()
        .id(id)
        .login("login".concat(id))
        .lastName("lastName".concat(id))
        .firstName("firstName".concat(id))
        .email("email".concat(id))
        .build();
  }
}
