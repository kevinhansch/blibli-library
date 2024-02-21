package com.gdn.blibli.library.command.impl.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.enums.BookStatus;
import com.gdn.blibli.library.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class CreateBookCommandImplTest {

  private static final String CODE = "BK001";
  private static final String TITLE = "title";
  private static final String AUTHOR = "author";
  private static final String PUBLISHER = "publisher";

  @InjectMocks
  private CreateBookCommandImpl commandImpl;

  @Mock
  private BookRepository repository;

  @BeforeEach
  public void setUp() {
    initMocks(this);
  }

  @AfterEach
  public void tearDown() {
    verifyNoMoreInteractions(this.repository);
  }

//  @Test
//  public void createBookCommandTest() {
//    CreateBookCommandRequest request = CreateBookCommandRequest.builder()
//        .code(CODE).title(TITLE).author(AUTHOR).publisher(PUBLISHER).build();
//
//    Book book = new Book();
//    book.setCode(CODE);
//    book.setTitle(TITLE);
//    book.setAuthor(AUTHOR);
//    book.setPublisher(PUBLISHER);
//    book.setStatus(BookStatus.AVAILABLE);
//
//    when(this.repository.save(book))
//        .thenReturn(Mono.just(book));
//
//    StepVerifier.create(this.commandImpl.execute(request)).assertNext(response -> {
//      assertNotNull(response);
//      assertEquals(CODE, response.getCode());
//      assertEquals(TITLE, response.getTitle());
//      assertEquals(AUTHOR, response.getAuthor());
//      assertEquals(PUBLISHER, response.getPublisher());
//      assertEquals(BookStatus.AVAILABLE.name(), response.getStatus());
//    }).expectComplete().verify();
//
//    verify(this.repository).save(book);
//  }
}
