package com.gdn.blibli.library.repository;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootApplication
@EnableMongoAuditing
@EnableReactiveMongoRepositories
@TestPropertySource(locations = {"/application.properties"},
        properties = {"spring.cloud.consul.enabled=false", "spring.cloud.vault.enabled=false"})
@ActiveProfiles("test")
public class TestSpringBootApp {
}
