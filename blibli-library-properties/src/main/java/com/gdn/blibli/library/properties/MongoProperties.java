package com.gdn.blibli.library.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "custom.mongodb")
public class MongoProperties {

  private String username;
  private String password;
  private String hosts;
  private String databaseName;
  private String authenticationDatabaseName;
  private int maxConnection;
  private int minConnection;
  private int maxWaitQueueSize; // renamed from threadsAllowedToBlockForConnectionMultiplier
  private int maxWaitTime;
  private int connectTimeout;
  private int readTimeout;
  private int heartbeatFrequency;
  private int maxConnectionIdleTime;
  private int maxConnectionLifeTime;
  private int minHeartbeatFrequency;
  private String readPreference;
  private String writeConcern;
}

