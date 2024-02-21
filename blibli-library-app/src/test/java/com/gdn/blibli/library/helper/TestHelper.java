package com.gdn.blibli.library.helper;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.blibli.library.IntegrationExtension;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHelper {

  @Autowired
  protected WebTestClient webTestClient;

  @Autowired
  private ObjectMapper objectMapper;

  public <T> T jsonFileToObject(String path, Class<T> clazz) {
    try {
      String json = getJsonByPath(path);
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      return objectMapper.readValue(json, clazz);
    } catch (Exception e) {
      log.error("Fail converting json to object, cause: {}", e.getMessage());
      return null;
    }
  }

  public <T> T jsonFileToObject(String path, TypeReference<T> typeReference) {
    try {
      String json = getJsonByPath(path);
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      return objectMapper.readValue(json, typeReference);
    } catch (Exception e) {
      log.error("Fail converting json to object, cause: {}", e.getMessage());
      return null;
    }
  }

  public String getJsonByPath(String path) {
    try {
      return IOUtils.toString(this.getClass().getResourceAsStream(path),
          Charset.defaultCharset().toString());
    } catch (IOException e) {
      log.error("Can't find json file for path: {}", path);
      return Strings.EMPTY;
    }
  }

  public Consumer<HttpHeaders> getDefaultHeaders() {
    return httpHeaders -> {
      httpHeaders.put("storeId", Collections.singletonList("10001"));
      httpHeaders.put("clientId", Collections.singletonList("blibli-library"));
      httpHeaders.put("requestId", Collections.singletonList("requestId"));
      httpHeaders.put("channelId", Collections.singletonList("web"));
      httpHeaders.put("username", Collections.singletonList("username"));
    };
  }

  public <T> T getExpectedData(TypeReference<T> typeReference, String fileName) {
    return jsonFileToObject(IntegrationExtension.getPath().getExpectation() + fileName,
        typeReference);
  }

  @SneakyThrows
  public static void sleep(int seconds) {
    Thread.sleep(seconds * 1000);
  }
}

