package com.gdn.blibli.library.command.impl.book;

import com.gdn.blibli.library.command.book.CreateBookCommand;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.enums.BookStatus;
import com.gdn.blibli.library.helper.ResponseHelper;
import com.gdn.blibli.library.repository.BookRepository;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CreateBookCommandImpl implements CreateBookCommand {

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Mono<CreateBookWebResponse> execute(CreateBookCommandRequest request) {
    return this.bookRepository.save(this.toBook(request))
        .map(this::toCreateBookWebResponse)
        .doOnError(
            e -> log.error("Error when #createBook with request: {}, errorMessage: {}",
                request, e.getMessage()));
  }

  private Book toBook(CreateBookCommandRequest request) {
    Book book = new Book();
    book.setCode(request.getCode());
    book.setTitle(request.getTitle());
    book.setAuthor(request.getAuthor());
    book.setPublisher(request.getPublisher());
    book.setStatus(BookStatus.AVAILABLE);
    return book;
  }

  private CreateBookWebResponse toCreateBookWebResponse(
      Book book) {
    CreateBookWebResponse response = CreateBookWebResponse.builder()
        .code(book.getCode())
        .title(book.getTitle())
        .author(book.getAuthor())
        .publisher(book.getPublisher())
        .status(book.getStatus().name())
        .build();

    return ResponseHelper.setBaseResponse(response, book);
  }
}
