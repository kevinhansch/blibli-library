package com.gdn.blibli.library.web;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.gdn.blibli.library.command.book.CreateBookCommand;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.enums.BookStatus;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.blibli.oss.backend.command.executor.CommandExecutor;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = TestSpringBootApp.class)
@Import(WebTestConfig.class)
public class BookControllerTest {
  private static final String CODE = "BK001";
  private static final String TITLE = "title";
  private static final String AUTHOR = "author";
  private static final String PUBLISHER = "publisher";

  @MockBean
  private CommandExecutor commandExecutor;

  @Autowired
  private WebTestClient webClient;

  @AfterEach
  public void tearDown() {
    verifyNoMoreInteractions(this.commandExecutor);
  }

//  @Test
//  public void createBookTest() {
//    CreateBookCommandRequest request = CreateBookCommandRequest.builder()
//        .code(CODE).title(TITLE).author(AUTHOR).publisher(PUBLISHER).build();
//    CreateBookWebResponse response = CreateBookWebResponse.builder()
//        .code(CODE).title(TITLE).author(AUTHOR).publisher(PUBLISHER)
//        .status(BookStatus.AVAILABLE.name()).build();
//
//    when(this.commandExecutor.execute(CreateBookCommand.class, request))
//        .thenReturn(Mono.just(response));
//
//    webClient.post().uri("/api/book").header("storeId", "storeId")
//        .header("requestId", "requestId").header("clientId", "clientId")
//        .header("channelId", "channelId").header("username", "username")
//        .body(BodyInserters.fromObject(request))
//        .exchange()
//        .expectStatus().isOk()
//        .expectBody().jsonPath("$.data.code").isEqualTo(CODE);
//
//    verify(this.commandExecutor).execute(CreateBookCommand.class, request);
//  }
}