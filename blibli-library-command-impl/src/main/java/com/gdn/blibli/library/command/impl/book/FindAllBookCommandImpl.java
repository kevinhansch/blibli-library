package com.gdn.blibli.library.command.impl.book;

import com.gdn.blibli.library.command.model.book.FindAllBookCommandRequest;
import com.gdn.blibli.library.command.status.FindAllBookCommand;
import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.enums.ErrorCodes;
import com.gdn.blibli.library.exceptions.BusinessException;
import com.gdn.blibli.library.helper.ResponseHelper;
import com.gdn.blibli.library.repository.BookRepository;
import com.gdn.blibli.library.web.model.book.FindAllBookWebResponse;
import com.gdn.blibli.library.web.model.book.FindBookWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class FindAllBookCommandImpl implements FindAllBookCommand {

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Mono<FindAllBookWebResponse> execute(FindAllBookCommandRequest request) {
    return this.bookRepository
        .findByIdNotNull(this.toPageRequest(request.getPage(), request.getSize()))
        .map(this::toFindBookWebResponse).collectList()
        .zipWith(this.bookRepository.count())
        .map(tuple -> this.toFindAllBookWebResponse(tuple.getT1(), tuple.getT2()))
        .switchIfEmpty(Mono.error(new BusinessException(ErrorCodes.DATA_NOT_FOUND)))
        .doOnError(e -> log.error(
            "Error when #findAllBook with page: {}, size: {}, errorMessage: {}",
            request.getPage(), request.getSize(), e.getMessage()));
  }

  private PageRequest toPageRequest(Long page, Long size) {
    return PageRequest.of(page.intValue() - 1, size.intValue());
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

  private FindAllBookWebResponse toFindAllBookWebResponse(
      List<FindBookWebResponse> responses, Long count) {
    return FindAllBookWebResponse.builder()
        .responses(responses)
        .total(count)
        .build();
  }
}
