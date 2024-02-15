package com.gdn.blibli.library.helper;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ClasspathFileSource;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSource;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubApiHelper {

  private static final String CONTENT_TYPE = "Content-Type";

  private static final ObjectMapper objectMapper;

  private static final Set<StubMapping> stubMappings = new HashSet<>();

  static {
    objectMapper = new ObjectMapper();
  }

  protected StubApiHelper() {}

  public static void before() {
    WireMock.reset();
    stubMappings.clear();
  }

  /**
   * @param stubOptions {@link StubOptions} with default httpStatus 200 and httpMethod GET
   * @return StubMapping
   */
  public static StubMapping createStub(StubOptions stubOptions) {
    try {
      StubMapping stubMapping = stubOptions.stub();
      StubApiHelper.replaceExistingStub(stubMapping);
      return stubMapping;
    } catch (Exception e) {
      log.error("#createStub with error : {}", e.getMessage());
      return null;
    }
  }

  /**
   * @param pathName the path name of the json file
   * @return list of StubMapping
   * @throws Exception
   */
  public static List<StubMapping> createStub(String pathName) throws Exception {
    return StubApiHelper.toJsonAndCreateStub(StubApiHelper.class.getResource(pathName).toURI());
  }

  /**
   * @param path of the root folder to be scanned
   */
  public static void createStubs(String path) {
    try {
      File selectedDirectory = new File(StubApiHelper.class.getResource(path).toURI());
      StubApiHelper.toJsonAndCreateStub(selectedDirectory);
    } catch (Exception e) {
      log.error("#createStubMultiples error : {}", e.getMessage());
    }
  }

  /**
   * @param wireMockServer {@link WireMockServer}
   * @param path of the root folder to be scanned
   */
  public static void createStubWireMockTemplate(WireMockServer wireMockServer, String path) {
    try {
      File selectedDirectory = new File(StubApiHelper.class.getResource(path).toURI());
      List<StubMapping> stubMappings = new ArrayList<>(wireMockServer.getStubMappings());
      ClasspathFileSource classpathFileSource =
          new ClasspathFileSource(selectedDirectory.getAbsolutePath());
      wireMockServer.loadMappingsUsing(new JsonFileMappingsSource(classpathFileSource));
      stubMappings.forEach(stubMapping -> wireMockServer.addStubMapping(stubMapping));
    } catch (Exception e) {
      log.error("#createStubWireMockTemplate with error : {}", e.getMessage());
    }
  }

  private static void replaceExistingStub(StubMapping stubMapping) {
    stubMappings.stream().filter(stub -> stub.getRequest().equals(stubMapping.getRequest()))
        .forEach(WireMock::removeStub);
    stubMappings.add(stubMapping);
  }

  private static void toJsonAndCreateStub(File root) {
    if (!root.exists()) {
      return;
    }
    if (root.isDirectory()) {
      if (root.listFiles().length < 1) {
        return;
      }
      for (File file : root.listFiles()) {
        StubApiHelper.toJsonAndCreateStub(file);
      }
    } else {
      if (!FilenameUtils.getExtension(root.getAbsolutePath()).equals("json")) {
        return;
      }
      StubApiHelper.toJsonAndCreateStub(root.toURI());
    }
  }

  private static List<StubMapping> toJsonAndCreateStub(URI uri) {
    try {
      String json = IOUtils.toString(uri);
      List<StubJsonModel> stubs = new ArrayList<>();
      if (json.startsWith("[")) {
        List<StubJsonModel> stubJsons =
            objectMapper.readValue(json, new TypeReference<List<StubJsonModel>>() {});
        stubs.addAll(stubJsons);
      } else {
        stubs.add(objectMapper.readValue(json, StubJsonModel.class));
      }
      return stubs.stream().map(StubApiHelper::toStubMapping).collect(Collectors.toList());
    } catch (Exception e) {
      log.error("#createStub error with URI : {}", uri, e);
      return null;
    }
  }

  private static StubMapping toStubMapping(StubJsonModel stubJsonModel) {
    return StubApiHelper.createStub(StubOptions.builder().url(stubJsonModel.getUrl())
        .httpMethod(HttpMethod.valueOf(stubJsonModel.getMethod()))
        .responseStatus(HttpStatus.valueOf(stubJsonModel.getStatus()))
        .queryParam(stubJsonModel.getParams()).requestBody(stubJsonModel.getRequest())
        .response(stubJsonModel.getResponse()).delay(stubJsonModel.getDelay())
        .priority(stubJsonModel.getPriority()).cookies(stubJsonModel.getCookies())
        .header(stubJsonModel.getHeader()).build());
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class StubJsonModel {
    private String url;
    private String method = "GET";
    private int status = 200;
    private Map<String, String> params;
    private Map<String, String> cookies;
    private Map<String, String> header;
    private Object request;
    private Object response;
    private int delay;
    private int priority;

    private enum RequestPattern {
      EQUAL_TO("$equalTo"),
      CONTAINS("$contains"),
      MATCHES("$matches");

      private String value;

      private static final Map<String, RequestPattern> PATTERN_MAP = new HashMap<>();

      static {
        for (RequestPattern pattern : values()) {
          PATTERN_MAP.put(pattern.value, pattern);
        }
      }

      static RequestPattern getByValue(String value) {
        return PATTERN_MAP.get(value);
      }

      RequestPattern(String value) {
        this.value = value;
      }

      public String getValue() {
        return value;
      }
    }
  }

  @Setter(AccessLevel.PRIVATE)
  @Getter(AccessLevel.PRIVATE)
  public static class StubOptions {
    private String url;
    private Object response;
    private Object requestBody;
    private int delay;
    private int priority;
    private String responseFilePath;
    private HttpMethod httpMethod;
    private HttpStatus responseStatus;
    private Map<String, String> queryParam = new HashMap<>();
    private ResponseDefinitionBuilder responseDefinitionBuilder;
    private MappingBuilder mappingBuilder;
    private Map<String, String> cookies = new HashMap<>();
    private Map<String, String> header = new HashMap<>();

    @Builder
    public StubOptions(String url, Object response, Object requestBody, String responseFilePath,
        int delay, int priority, HttpMethod httpMethod, HttpStatus responseStatus,
        Map<String, String> queryParam, Map<String, String> cookies, Map<String, String> header) {
      this.url = url;
      this.response = response;
      this.delay = delay;
      this.priority = priority;
      this.requestBody = requestBody;
      this.responseFilePath = responseFilePath;
      this.httpMethod = Optional.ofNullable(httpMethod).orElse(HttpMethod.GET);
      this.responseStatus = Optional.ofNullable(responseStatus).orElse(HttpStatus.OK);
      this.queryParam.putAll(Optional.ofNullable(queryParam).orElse(new HashMap<>()));
      this.cookies.putAll(Optional.ofNullable(cookies).orElse(new HashMap<>()));
      this.header.putAll(Optional.ofNullable(header).orElse(new HashMap<>()));
    }

    public ResponseDefinitionBuilder addResponse() {
      return responseDefinitionBuilder;
    }

    public MappingBuilder addRequest() {
      return mappingBuilder;
    }

    private StubMapping stub() throws Exception {
      MappingBuilder mappingBuilder = this.getMappingBuilder();

      if (Objects.nonNull(this.getRequestBody())) {
        this.addRequestBodyToMappingBuilder();
      }

      if (MapUtils.isNotEmpty(this.getQueryParam())) {
        this.getQueryParam()
            .forEach((key, value) -> mappingBuilder.withQueryParam(key, WireMock.matching(value)));
      }

      if (MapUtils.isNotEmpty(this.getCookies())) {
        this.getCookies()
            .forEach((key, value) -> mappingBuilder.withCookie(key, WireMock.matching(value)));
      }

      if (MapUtils.isNotEmpty(this.getHeader())) {
        this.getHeader()
                .forEach((key, value) -> mappingBuilder.withHeader(key, WireMock.matching(value)));
      }
      
      this.getResponseDefinitionBuilder().withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .withBody(objectMapper.writeValueAsString(this.getResponse()))
          .withBodyFile(this.getResponseFilePath()).withFixedDelay(this.getDelay());

      StubMapping stubMapping = WireMock
          .stubFor(this.getMappingBuilder().willReturn(this.getResponseDefinitionBuilder()));
      stubMapping.setPriority(getPriority());
      return stubMapping;
    }

    private void addRequestBodyToMappingBuilder() throws Exception {
      String requestValue = objectMapper.writeValueAsString(this.getRequestBody());
      if (requestValue.contains("$")) {
        Map<String, Object> requestMap = objectMapper.readValue(requestValue, Map.class);
        List<StubJsonModel.RequestPattern> requestPatterns =
            Optional.ofNullable(requestMap).map(Map::keySet).orElse(new HashSet<>()).stream()
                .map(StubJsonModel.RequestPattern::getByValue).filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(requestPatterns)) {
          mappingBuilder.withRequestBody(WireMock.equalToJson(requestValue, true, true));
        } else {
          for (StubJsonModel.RequestPattern requestPattern : requestPatterns) {
            mappingBuilder.withRequestBody(
                this.toValuePattern(requestPattern, requestMap.get(requestPattern.getValue())));
          }
        }
      } else {
        mappingBuilder.withRequestBody(WireMock.equalToJson(requestValue, true, true));
      }
    }

    private StringValuePattern toValuePattern(StubJsonModel.RequestPattern requestPattern,
        Object value) throws Exception {
      String valueString = objectMapper.writeValueAsString(value);
      switch (requestPattern) {
        case MATCHES:
          return WireMock.matching(valueString);
        case CONTAINS:
          return WireMock.containing(valueString);
        case EQUAL_TO:
        default:
          return WireMock.equalToJson(valueString, true, true);
      }
    }

    public static StubOptionsBuilder builder() {
      return new StubOptionsBuilder() {
        @Override
        public StubOptions build() {
          StubOptions stubOptions = super.build();

          stubOptions.setResponseDefinitionBuilder(
              WireMock.aResponse().withStatus(stubOptions.getResponseStatus().value()));

          stubOptions.setMappingBuilder(toMappingBuilder(stubOptions));

          return stubOptions;
        }

        private MappingBuilder toMappingBuilder(StubOptions stubOptions) {
          switch (stubOptions.getHttpMethod()) {
            case DELETE:
              return WireMock.delete(WireMock.urlPathEqualTo(stubOptions.getUrl()));
            case POST:
              return WireMock.post(WireMock.urlPathEqualTo(stubOptions.getUrl()));
            case PUT:
              return WireMock.put(WireMock.urlPathEqualTo(stubOptions.getUrl()));
            case GET:
            default:
              return WireMock.get(WireMock.urlPathEqualTo(stubOptions.getUrl()));
          }
        }
      };
    }
  }
}

