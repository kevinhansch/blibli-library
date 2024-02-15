package com.gdn.blibli.library;

import com.blibli.oss.backend.mandatoryparameter.sleuth.MandatoryParameterSleuthAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = "com.gdn.blibli.library",
    exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
        MandatoryParameterSleuthAutoConfiguration.class})
@EntityScan("com.gdn.blibli.library.entity")
@ConfigurationPropertiesScan("com.gdn.blibli.library.properties")
public class BlibliLibraryApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlibliLibraryApplication.class, args);
  }
}
