package com.gdn.blibli.library.web;

import com.blibli.oss.backend.command.executor.CommandExecutor;
import com.blibli.oss.backend.common.helper.ResponseHelper;
import com.blibli.oss.backend.common.model.response.Response;
import com.blibli.oss.backend.mandatoryparameter.swagger.annotation.MandatoryParameterAtHeader;
import com.gdn.blibli.library.command.demo.Demo1Command;
import com.gdn.blibli.library.command.demo.Demo2Command;
import com.gdn.blibli.library.command.demo.Demo3Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping
  public Mono<Response<Boolean>> demo() {
    log.info("start hitting #demo");
    this.commandExecutor.execute(Demo1Command.class, "")
        .subscribeOn(scheduler).subscribe();
    this.commandExecutor.execute(Demo2Command.class, "")
        .subscribeOn(scheduler).subscribe();
    return Mono.just(ResponseHelper.ok(Boolean.TRUE));
  }
}
