package com.gdn.blibli.library.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("scheduler")
public class SchedulerProperties {

  private Long delayInMillis = 150L;
}
