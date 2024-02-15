package com.gdn.blibli.library.command.impl.book;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;

import com.gdn.blibli.library.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;

public class CreateBookCommandImplTest {

  @InjectMocks
  private CreateBookCommandImpl commandImpl;

  @Mock
  private BookRepository repository;

  private CreateBookCommandRequest request;

  @BeforeEach
  public void setUp() {
    initMocks(this);
  }

  @AfterEach
  public void tearDown() {
    verifyNoMoreInteractions(this.repository);
  }
}
