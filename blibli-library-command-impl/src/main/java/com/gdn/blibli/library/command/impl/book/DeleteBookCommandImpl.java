package com.gdn.blibli.library.command.impl.book;

import com.gdn.blibli.library.command.book.DeleteBookCommand;
import com.gdn.blibli.library.command.model.book.DeleteBookCommandRequest;
import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.enums.ErrorCodes;
import com.gdn.blibli.library.exceptions.BusinessException;
import com.gdn.blibli.library.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DeleteBookCommandImpl implements DeleteBookCommand {

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Mono<Boolean> execute(DeleteBookCommandRequest request) {
    return this.bookRepository.findByCode(request.getCode())
        .switchIfEmpty(Mono.error(new BusinessException(ErrorCodes.DATA_NOT_FOUND)))
        .flatMap(this::delete)
        .doOnError(
            e -> log.error("Error when #deleteBook with code: {}, errorMessage: {}",
                request.getCode(), e.getMessage()));
  }

  private Mono<Boolean> delete(Book book) {
    return this.bookRepository.delete(book).thenReturn(Boolean.TRUE);
  }
}
