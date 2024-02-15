package com.gdn.blibli.library.helper;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaTestHelper {

  private final ObjectMapper objectMapper;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private final ConcurrentHashMap<String, ConsumerRecord<String, String>> messages;

  @Autowired
  public KafkaTestHelper(ObjectMapper objectMapper, KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
    this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    this.messages = new ConcurrentHashMap<>();
  }

  public void sendMessage(String topic, Object message) {
    this.kafkaTemplate.send(topic, message);
  }

  public <T> T getMessageByTopic(String topic, Class<T> clazz, int timeoutInSeconds) {
    return getMessage(topic, clazz, timeoutInSeconds);
  }

  public <T> T getMessageByTopic(String topic, Class<T> clazz) {
    return getMessage(topic, clazz, 30);
  }

  public ConsumerRecord<String, String> getMessageByTopic(String topic) {
    return getMessageWithTimeout(topic, 30);
  }

  private <T> T getMessage(String topic, Class<T> clazz, int timeoutInSeconds) {
    T object = null;
    try {
      String message = Optional.ofNullable(this.getMessageWithTimeout(topic, timeoutInSeconds))
          .map(ConsumerRecord::value).orElse(null);
      if (StringUtils.isNotBlank(message))
        object = objectMapper.readValue(message, clazz);
    } catch (Exception e) {
      log.error("Error #getMessage with error : {}", e.getMessage(), e);
    }
    return object;
  }

  private ConsumerRecord<String, String> getMessageWithTimeout(String topic, int timeoutInSeconds) {
    long timeout = DateUtils.addSeconds(new Date(), timeoutInSeconds).getTime();
    ConsumerRecord<String, String> message;
    while (true) {
      message = this.messages.remove(topic);
      if (message != null)
        break;
      else if (System.currentTimeMillis() >= timeout) {
        return messages.remove(topic);
      }
      sleep();
    }
    return message;
  }

  @SneakyThrows
  private void sleep() {
    Thread.sleep(100);
  }

  public void after() {
    this.messages.clear();
  }

}
