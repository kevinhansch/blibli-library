package com.gdn.blibli.library.command.impl.book;

import com.gdn.blibli.library.command.model.book.UpdateBookCommandRequest;
import com.gdn.blibli.library.command.status.UpdateBookCommand;
import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.enums.ErrorCodes;
import com.gdn.blibli.library.exceptions.BusinessException;
import com.gdn.blibli.library.repository.BookRepository;
import com.gdn.blibli.library.web.model.book.UpdateBookWebResponse;
import com.gdn.blibli.library.helper.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UpdateBookCommandImpl implements UpdateBookCommand {

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Mono<UpdateBookWebResponse> execute(UpdateBookCommandRequest request) {
    return this.bookRepository.findByCode(request.getCode())
        .map(book -> this.updateBookFields(book, request))
        .flatMap(bookRepository::save).map(this::toUpdateBookWebResponse)
        .switchIfEmpty(Mono.error(new BusinessException(ErrorCodes.DATA_NOT_FOUND)))
        .doOnError(
            e -> log.error("Error when #updateBook with request: {}, errorMessage: {}",
                request, e.getMessage(), e));
  }

  private Book updateBookFields(Book book, UpdateBookCommandRequest request) {
    book.setTitle(request.getTitle());
    book.setAuthor(request.getAuthor());
    book.setPublisher(request.getPublisher());
    return book;
  }

  private UpdateBookWebResponse toUpdateBookWebResponse(
      Book book) {
    UpdateBookWebResponse response = UpdateBookWebResponse.builder()
        .code(book.getCode())
        .title(book.getTitle())
        .author(book.getAuthor())
        .publisher(book.getPublisher())
        .status(book.getStatus().name())
        .build();

    return ResponseHelper.setBaseResponse(response, book);
  }
}
