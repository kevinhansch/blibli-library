package com.gdn.blibli.library.command.impl.book;

import com.gdn.blibli.library.command.model.book.FindOneBookCommandRequest;
import com.gdn.blibli.library.command.status.FindOneBookCommand;
import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.helper.ResponseHelper;
import com.gdn.blibli.library.repository.BookRepository;
import com.gdn.blibli.library.web.model.book.FindBookWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FindOneBookCommandImpl implements FindOneBookCommand {

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Mono<FindBookWebResponse> execute(FindOneBookCommandRequest request) {
    return this.bookRepository.findByCode(request.getCode())
        .map(this::toFindBookWebResponse)
        .doOnError(
            e -> log.error("Error when #createBook with request: {}, errorMessage: {}",
                request, e.getMessage()));
  }

  private FindBookWebResponse toFindBookWebResponse(
      Book book) {
    FindBookWebResponse response = FindBookWebResponse.builder()
        .code(book.getCode())
        .title(book.getTitle())
        .author(book.getAuthor())
        .publisher(book.getPublisher())
        .status(book.getStatus().name())
        .build();

    return ResponseHelper.setBaseResponse(response, book);
  }
}
