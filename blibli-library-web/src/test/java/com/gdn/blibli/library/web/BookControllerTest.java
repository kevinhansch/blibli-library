package com.gdn.blibli.library.web;

import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.blibli.oss.backend.command.executor.CommandExecutor;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestSpringBootApp.class)
@Import(WebTestConfig.class)
public class BookControllerTest {

  @MockBean
  private CommandExecutor commandExecutor;

  @Autowired
  private WebTestClient webClient;

  @AfterEach
  public void tearDown() {
    verifyNoMoreInteractions(this.commandExecutor);
  }
}