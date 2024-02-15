package com.gdn.blibli.library.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class WebTestConfig {

  @Bean
  public Scheduler commandScheduler() {
    return Schedulers.boundedElastic();
  }
}
