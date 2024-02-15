package com.gdn.blibli.library.web;

import com.gdn.blibli.library.web.annotation.PostHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blibli.oss.backend.command.executor.CommandExecutor;
import com.blibli.oss.backend.common.helper.ResponseHelper;
import com.blibli.oss.backend.common.model.response.Response;
import com.blibli.oss.backend.mandatoryparameter.swagger.annotation.MandatoryParameterAtHeader;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.command.status.CreateBookCommand;
import com.gdn.blibli.library.web.model.book.CreateBookWebRequest;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

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
      @RequestBody CreateBookWebRequest createSystemParamWebRequest) {
    log.info("#createBook with request: {}", createSystemParamWebRequest);
    return this.commandExecutor
        .execute(CreateBookCommand.class,
            CreateBookCommandRequest.builder()
                .variable(createSystemParamWebRequest.getVariable())
                .value(createSystemParamWebRequest.getValue())
                .description(createSystemParamWebRequest.getDescription()).build())
        .map(ResponseHelper::ok).subscribeOn(scheduler);
  }
}
