/*
 * Copyright (c) 2022 Raman Kandratsenka.
 * All rights reserved.
 */

package com.dama.ohrringe.config;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/** The Mongo configuration. */
@Configuration
@EnableMongoAuditing
@EnableMongock
public class MongoConfig {}
