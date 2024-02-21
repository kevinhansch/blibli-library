package com.gdn.blibli.library.controller;

import com.blibli.oss.backend.common.model.response.Response;
import com.gdn.blibli.library.IntegrationTestAbstract;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.enums.BookStatus;
import com.gdn.blibli.library.repository.BookRepository;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.BodyInserters;

public class BookControllerIntegrationTest extends IntegrationTestAbstract {
  private static final String CODE = "BK001";
  private static final String TITLE = "title";
  private static final String AUTHOR = "author";
  private static final String PUBLISHER = "publisher";

  @Autowired
  private BookRepository bookRepository;

  @BeforeEach
  public void setUp() {
  }

  @AfterEach
  public void tearDown() {
    this.bookRepository.deleteAll().block();
  }

//  @Test
//  public void createBookTest() {
//    CreateBookCommandRequest request = CreateBookCommandRequest.builder()
//        .code(CODE).title(TITLE).author(AUTHOR).publisher(PUBLISHER).build();
//
//    webTestClient.post()
//        .uri(uriBuilder -> uriBuilder.path("/api/book").build())
//        .headers(getDefaultHeaders()).body(BodyInserters.fromObject(request)).exchange()
//        .expectStatus().isOk()
//        .expectBody(new ParameterizedTypeReference<Response<CreateBookWebResponse>>() {})
//        .value(response -> {
//          Assertions.assertNull(response.getErrors());
//          Assertions.assertEquals(200, response.getCode());
//          Assertions.assertEquals(CODE, response.getData().getCode());
//          Assertions.assertEquals(TITLE, response.getData().getTitle());
//          Assertions.assertEquals(AUTHOR, response.getData().getAuthor());
//          Assertions.assertEquals(PUBLISHER, response.getData().getPublisher());
//          Assertions.assertEquals(BookStatus.AVAILABLE.name(), response.getData().getStatus());
//        });
//
//    Book savedBook = this.bookRepository.findAll().blockFirst();
//    Assertions.assertNotNull(savedBook);
//    Assertions.assertEquals(CODE, savedBook.getCode());
//  }
}

