package com.gdn.blibli.library.web;

import com.blibli.oss.backend.command.executor.CommandExecutor;
import com.blibli.oss.backend.common.helper.ResponseHelper;
import com.blibli.oss.backend.common.model.response.Response;
import com.blibli.oss.backend.mandatoryparameter.swagger.annotation.MandatoryParameterAtHeader;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.command.book.CreateBookCommand;
import com.gdn.blibli.library.web.annotation.PostHeaders;
import com.gdn.blibli.library.web.model.book.CreateBookWebRequest;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Slf4j
@RestController
@RequestMapping(path = "/api/demo",
    produces = MediaType.APPLICATION_JSON_VALUE)
@MandatoryParameterAtHeader
public class DemoController {

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
}
