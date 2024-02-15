package com.gdn.blibli.library.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;

@Configuration
public class MandatoryParameterConfiguration {

  @Bean
  public ExtraFieldPropagation.Factory extraFieldPropagation() {
    return ExtraFieldPropagation.newFactoryBuilder(B3Propagation.FACTORY)
        .addField("storeId")
        .addField("channelId")
        .addField("clientId")
        .addField("requestId")
        .addField("username")
        .addField("key")
        .build();
  }
}
