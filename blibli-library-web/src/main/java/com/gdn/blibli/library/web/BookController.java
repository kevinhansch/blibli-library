package com.gdn.blibli.library.web;

import com.blibli.oss.backend.common.helper.PagingHelper;
import com.blibli.oss.backend.common.model.request.PagingRequest;
import com.blibli.oss.backend.common.model.response.Paging;
import com.blibli.oss.backend.common.swagger.annotation.PagingRequestInQuery;
import com.gdn.blibli.library.command.book.BorrowBookCommand;
import com.gdn.blibli.library.command.book.FindBookByFilterCommand;
import com.gdn.blibli.library.command.model.book.BorrowBookCommandRequest;
import com.gdn.blibli.library.command.model.book.DeleteBookCommandRequest;
import com.gdn.blibli.library.command.model.book.FindAllBookCommandRequest;
import com.gdn.blibli.library.command.model.book.FindBookByFilterCommandRequest;
import com.gdn.blibli.library.command.model.book.FindOneBookCommandRequest;
import com.gdn.blibli.library.command.model.book.UpdateBookCommandRequest;
import com.gdn.blibli.library.command.book.DeleteBookCommand;
import com.gdn.blibli.library.command.book.FindAllBookCommand;
import com.gdn.blibli.library.command.book.FindOneBookCommand;
import com.gdn.blibli.library.command.book.UpdateBookCommand;
import com.gdn.blibli.library.enums.BookStatus;
import com.gdn.blibli.library.web.annotation.DeleteHeaders;
import com.gdn.blibli.library.web.annotation.PostHeaders;
import com.gdn.blibli.library.web.annotation.PutHeaders;
import com.gdn.blibli.library.web.model.book.BorrowBookWebRequest;
import com.gdn.blibli.library.web.model.book.FindBookWebResponse;
import com.gdn.blibli.library.web.model.book.UpdateBookWebRequest;
import com.gdn.blibli.library.web.model.book.UpdateBookWebResponse;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blibli.oss.backend.command.executor.CommandExecutor;
import com.blibli.oss.backend.common.helper.ResponseHelper;
import com.blibli.oss.backend.common.model.response.Response;
import com.blibli.oss.backend.mandatoryparameter.swagger.annotation.MandatoryParameterAtHeader;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.command.book.CreateBookCommand;
import com.gdn.blibli.library.web.model.book.CreateBookWebRequest;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/book",
    produces = MediaType.APPLICATION_JSON_VALUE)
@MandatoryParameterAtHeader
public class BookController {

  @Autowired
  private CommandExecutor commandExecutor;

  @Autowired
  @Qualifier(value = "commandScheduler")
  private Scheduler scheduler;

  @PostHeaders
  public Mono<Response<CreateBookWebResponse>> create(
      @RequestBody CreateBookWebRequest createBookWebRequest) {
    log.info("#createBook with request: {}", createBookWebRequest);
    return this.commandExecutor
        .execute(CreateBookCommand.class,
            CreateBookCommandRequest.builder()
                .code(createBookWebRequest.getCode())
                .title(createBookWebRequest.getTitle())
                .author(createBookWebRequest.getAuthor())
                .publisher(createBookWebRequest.getPublisher()).build())
        .map(ResponseHelper::ok).subscribeOn(scheduler);
  }

  @PutHeaders
  public Mono<Response<UpdateBookWebResponse>> update(@RequestParam String code,
      @RequestBody UpdateBookWebRequest updateBookWebRequest) {
    log.info("#updateBook with code: {}, request: {}", code, updateBookWebRequest);
    return this.commandExecutor
        .execute(UpdateBookCommand.class,
            UpdateBookCommandRequest.builder()
                .code(code)
                .title(updateBookWebRequest.getTitle())
                .author(updateBookWebRequest.getAuthor())
                .publisher(updateBookWebRequest.getPublisher()).build())
        .map(ResponseHelper::ok).subscribeOn(scheduler);
  }

  @DeleteHeaders
  public Mono<Response<Boolean>> delete(
      @RequestParam String code) {
    log.info("#deleteBook with code: {}", code);
    return this.commandExecutor
        .execute(DeleteBookCommand.class,
            DeleteBookCommandRequest.builder().code(code).build())
        .map(ResponseHelper::ok).subscribeOn(scheduler);
  }

  @GetMapping
  public Mono<Response<FindBookWebResponse>> findOne(
      @RequestParam String code) {
    log.info("#findOneBook with code: {}", code);
    return this.commandExecutor
        .execute(FindOneBookCommand.class,
            FindOneBookCommandRequest.builder()
                .code(code).build())
        .map(ResponseHelper::ok).subscribeOn(scheduler);
  }

  @GetMapping(path = "/all")
  @PagingRequestInQuery
  public Mono<Response<List<FindBookWebResponse>>> findAll(PagingRequest pagingRequest) {
    log.info("#findAllBook with pagingRequest: {}", pagingRequest);
    return this.commandExecutor
        .execute(FindAllBookCommand.class, FindAllBookCommandRequest.builder()
            .page(pagingRequest.getPage()).size(pagingRequest.getItemPerPage()).build())
        .map(response -> {
          Paging paging = PagingHelper.toPaging(pagingRequest, response.getTotal());
          return ResponseHelper.ok(response.getResponses(), paging);
        }).subscribeOn(scheduler);
  }

  @GetMapping(path = "/findByFilter")
  @PagingRequestInQuery
  public Mono<Response<List<FindBookWebResponse>>> findByFilter(
      @RequestParam(required = false) String bookCode,
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String author,
      @RequestParam(required = false) String publisher,
      @RequestParam(required = false) String bookStatus,
      PagingRequest pagingRequest) {
    log.info("#findBookByFilter with bookCode: {}, title: {}, author: {}," +
        "publisher: {}, bookStatus: {}, pagingRequest: {}", bookCode, title,
        author, publisher, bookStatus, pagingRequest);
    return this.commandExecutor
        .execute(FindBookByFilterCommand.class, FindBookByFilterCommandRequest.builder()
            .bookCode(bookCode).title(title).author(author).publisher(publisher)
            .bookStatus(EnumUtils.getEnum(BookStatus.class, bookStatus))
            .page(pagingRequest.getPage()).size(pagingRequest.getItemPerPage()).build())
        .map(response -> {
          Paging paging = PagingHelper.toPaging(pagingRequest, response.getTotal());
          return ResponseHelper.ok(response.getResponses(), paging);
        }).subscribeOn(scheduler);
  }

  @PostHeaders
  public Mono<Response<Boolean>> borrowBook(
      @RequestBody BorrowBookWebRequest borrowBookWebRequest) {
    log.info("#borrowBook with request: {}", borrowBookWebRequest);
    return this.commandExecutor
        .execute(BorrowBookCommand.class,
            BorrowBookCommandRequest.builder()
                .memberId(borrowBookWebRequest.getMemberId())
                .bookCode(borrowBookWebRequest.getBookCode()).build())
        .map(ResponseHelper::ok).subscribeOn(scheduler);
  }
}
