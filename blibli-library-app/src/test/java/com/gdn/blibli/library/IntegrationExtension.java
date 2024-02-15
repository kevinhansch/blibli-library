package com.gdn.blibli.library;

import java.util.Arrays;
import java.util.List;

import com.gdn.blibli.library.helper.StubApiHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class IntegrationExtension implements BeforeEachCallback {

  private static Path path;

  private static final String BASE_JSON_PATH = "/json/%s-%s/%s/%s/";
  private static final String API_PATH = BASE_JSON_PATH + "api-mock/";
  private static final String EXPECTATION_PATH = BASE_JSON_PATH + "expectation/";

  public static Path getPath() {
    return path;
  }

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    path = constructPath(extensionContext);

    StubApiHelper.before();
  }


  private Path constructPath(ExtensionContext extensionContext) {
    List<String> strings =
        Arrays.asList(extensionContext.getRequiredTestClass().getCanonicalName().split("\\."));
    String packageName = strings.get(strings.size() - 3);
    String directoryName =
        String.join("-", StringUtils.splitByCharacterTypeCamelCase(strings.get(strings.size() - 2)))
            .toLowerCase();
    String className = Character.toLowerCase(strings.get(strings.size() - 1).charAt(0))
        + strings.get(strings.size() - 1).substring(1).replace("IntegrationTest", "");
    String scenarioName = extensionContext.getRequiredTestMethod().getName();
    return Path.builder()
        .apiMock(String.format(API_PATH, directoryName, packageName, className, scenarioName))
        .base(String.format(BASE_JSON_PATH, directoryName, packageName, className, scenarioName))
        .expectation(
            String.format(EXPECTATION_PATH, directoryName, packageName, className, scenarioName))
        .build();
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Path {
    private String base;
    private String apiMock;
    private String expectation;
  }
}
