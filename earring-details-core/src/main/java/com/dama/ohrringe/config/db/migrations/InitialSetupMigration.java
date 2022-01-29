/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.config.db.migrations;

import com.dama.ohrringe.domain.Authority;
import com.dama.ohrringe.domain.PriceConfig;
import com.dama.ohrringe.domain.User;
import com.dama.ohrringe.security.UserRole;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Stream;

/** Creates the initial database setup. */
@ChangeLog(order = "001")
public class InitialSetupMigration {

  private static final String ADMIN_NAME = "admin";
  private static final String SYSTEM = "system";

  /**
   * Populate authorities in the database.
   *
   * @param mongoTemplate the mongo DB template
   */
  @ChangeSet(order = "001", author = "initiator", id = "01-addAuthorities")
  public void addAuthorities(MongockTemplate mongoTemplate) {
    Stream.of(UserRole.values())
        .forEachOrdered(
            role -> {
              Authority authority = Authority.builder().name(role.getRoleName()).build();
              mongoTemplate.save(authority);
            });
  }

  /**
   * Populate users in the database.
   *
   * @param mongockTemplate the mongo DB template
   */
  @ChangeSet(order = "002", author = "initiator", id = "02-addUsers")
  public void addUsers(MongockTemplate mongockTemplate) {

    Authority adminAuthority = Authority.builder().name(UserRole.ADMIN.getRoleName()).build();
    Authority userAuthority = Authority.builder().name(UserRole.USER.getRoleName()).build();

    User admin =
        User.builder()
            .id("user-1")
            .login(ADMIN_NAME)
            .password("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC")
            .firstName(ADMIN_NAME)
            .lastName("Administrator")
            .email("admin@localhost")
            .activated(true)
            .langKey("en")
            .authorities(Set.of(adminAuthority, userAuthority))
            .createdBy(SYSTEM)
            .createdDate(Instant.now())
            .build();

    mongockTemplate.save(admin);

    User user =
        User.builder()
            .id("user-2")
            .login("user")
            .password("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K")
            .firstName("")
            .lastName("User")
            .email("user@localhost")
            .activated(true)
            .langKey("en")
            .createdBy(SYSTEM)
            .createdDate(Instant.now())
            .authorities(Set.of(userAuthority))
            .build();

    mongockTemplate.save(user);

    User dama =
        User.builder()
            .id("user-3")
            .login("dama")
            .password("$2a$10$o77E13o.nS0qULUN8DFqVODhCMBrxhZMGD5rhDp5Fb7rkj5wBQpei")
            .firstName(ADMIN_NAME)
            .lastName("Administrator")
            .email("dama@gmail.com")
            .activated(true)
            .langKey("en")
            .authorities(Set.of(adminAuthority, userAuthority))
            .createdBy(SYSTEM)
            .createdDate(Instant.now())
            .build();

    mongockTemplate.save(dama);
  }

  @ChangeSet(order = "003", author = "initiator", id = "03-addPriceConfig")
  public void addPriceConfig(MongockTemplate mongockTemplate) {
    PriceConfig priceConfig = PriceConfig.builder().premiumRate(5).deliveryPrice(5).build();
    mongockTemplate.save(priceConfig);
  }
}
