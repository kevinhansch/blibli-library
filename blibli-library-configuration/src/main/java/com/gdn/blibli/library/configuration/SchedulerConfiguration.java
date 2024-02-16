package com.gdn.blibli.library.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blibli.oss.backend.reactor.scheduler.SchedulerHelper;

import reactor.core.scheduler.Scheduler;

@Configuration
public class SchedulerConfiguration {

  @Autowired
  private SchedulerHelper schedulerHelper;

  @Bean
  public Scheduler commandScheduler() {
    return schedulerHelper.of("COMMAND");
  }
}
