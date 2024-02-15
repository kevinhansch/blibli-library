package com.gdn.blibli.library.configuration;

import com.gdn.blibli.library.properties.MongoProperties;
import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ClusterType;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.ServerSettings;
import com.mongodb.connection.SocketSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration
@EnableReactiveMongoRepositories("com.gdn.blibli.library.repository")
@AutoConfigureBefore({MongoReactiveAutoConfiguration.class,
    MongoReactiveDataAutoConfiguration.class})
@EnableReactiveMongoAuditing
@ConditionalOnClass({MongoProperties.class})
public class MongoConfiguration extends AbstractReactiveMongoConfiguration {

  @Autowired
  private MongoProperties mongoProperties;

  @Override
  protected String getDatabaseName() {
    return this.mongoProperties.getDatabaseName();
  }

  @Override
  public MongoClient reactiveMongoClient() {
    return MongoClients.create(this.mongoClientSettings());
  }

  @Override
  protected Collection<String> getMappingBasePackages() {
    return Collections.singletonList("com.gdn.blibli.library.entity");
  }

  @Override
  protected MongoClientSettings mongoClientSettings() {
    String authenticationDatabaseName =
        StringUtils.isNotBlank(this.mongoProperties.getAuthenticationDatabaseName())
            ? this.mongoProperties.getAuthenticationDatabaseName()
            : this.mongoProperties.getDatabaseName();
    return MongoClientSettings.builder()
        .applyToClusterSettings(this.toClusterSettings(mongoProperties))
        .applyToConnectionPoolSettings(this.toConnectionPoolSettings(mongoProperties))
        .readPreference(ReadPreference.valueOf(mongoProperties.getReadPreference()))
        .writeConcern(WriteConcern.valueOf(mongoProperties.getWriteConcern()))
        .applyToSocketSettings(this.toSocketSettings(mongoProperties.getConnectTimeout(),
            mongoProperties.getReadTimeout()))
        .applyToServerSettings(this.toServerSettings(mongoProperties))
        .credential(MongoCredential.createCredential(mongoProperties.getUsername(),
            authenticationDatabaseName, mongoProperties.getPassword().toCharArray()))
        .applicationName(mongoProperties.getDatabaseName()).build();
  }

  @Bean
  public ReactiveMongoTemplate mongoTemplate() {
    return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
  }

  @Bean
  public ReactiveAuditorAware<String> stringAuditorAware() {
    return new ReactiveAuditorAwareConfiguration();
  }

  private Block<ConnectionPoolSettings.Builder> toConnectionPoolSettings(
      MongoProperties mongoProperties) {
    return new Block<ConnectionPoolSettings.Builder>() {
      @Override
      public void apply(final ConnectionPoolSettings.Builder builder) {
        builder.maxSize(mongoProperties.getMaxConnection())
            .minSize(mongoProperties.getMinConnection())
            .maxWaitTime(mongoProperties.getMaxWaitTime(), TimeUnit.MILLISECONDS)
            .maxConnectionLifeTime(mongoProperties.getMaxConnectionLifeTime(),
                TimeUnit.MILLISECONDS)
            .maxConnectionIdleTime(mongoProperties.getMaxConnectionIdleTime(),
                TimeUnit.MILLISECONDS);
      }
    };
  }

  private Block<SocketSettings.Builder> toSocketSettings(int connectTimeout, int readTimeout) {
    return new Block<SocketSettings.Builder>() {
      @Override
      public void apply(SocketSettings.Builder builder) {
        builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        builder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
      }
    };
  }

  private Block<ServerSettings.Builder> toServerSettings(MongoProperties mongoProperties) {
    return new Block<ServerSettings.Builder>() {
      @Override
      public void apply(ServerSettings.Builder builder) {
        builder.heartbeatFrequency(mongoProperties.getHeartbeatFrequency(), TimeUnit.MILLISECONDS)
            .minHeartbeatFrequency(mongoProperties.getMinHeartbeatFrequency(),
                TimeUnit.MILLISECONDS);
      }
    };
  }

  private Block<ClusterSettings.Builder> toClusterSettings(MongoProperties mongoProperties) {
    return new Block<ClusterSettings.Builder>() {
      @Override
      public void apply(ClusterSettings.Builder builder) {
        Pair<ClusterType, List<ServerAddress>> pair = toServerAddresses(mongoProperties);
        builder.hosts(pair.getRight())
            .requiredClusterType(pair.getLeft());
      }
    };
  }

  private Pair<ClusterType, List<ServerAddress>> toServerAddresses(
      MongoProperties mongoProperties) {
    String[] serverAddressesString = mongoProperties.getHosts().split(",");
    ClusterType type =
        serverAddressesString.length == 1 ? ClusterType.STANDALONE : ClusterType.REPLICA_SET;
    return Pair.of(type, Arrays.stream(serverAddressesString).map(each -> {
      String[] addresses = each.split(":");
      return new ServerAddress(addresses[0], Integer.valueOf(addresses[1]));
    }).collect(Collectors.toList()));
  }
}
