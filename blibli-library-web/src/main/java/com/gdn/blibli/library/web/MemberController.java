package com.gdn.blibli.library.web;

import com.blibli.oss.backend.command.executor.CommandExecutor;
import com.blibli.oss.backend.common.helper.ResponseHelper;
import com.blibli.oss.backend.common.model.response.Response;
import com.blibli.oss.backend.mandatoryparameter.swagger.annotation.MandatoryParameterAtHeader;
import com.gdn.blibli.library.command.member.CreateMemberCommand;
import com.gdn.blibli.library.command.member.UpdateMemberCommand;
import com.gdn.blibli.library.command.model.member.CreateMemberCommandRequest;
import com.gdn.blibli.library.command.model.member.UpdateMemberCommandRequest;
import com.gdn.blibli.library.web.annotation.PostHeaders;
import com.gdn.blibli.library.web.annotation.PutHeaders;
import com.gdn.blibli.library.web.model.member.CreateMemberWebRequest;
import com.gdn.blibli.library.web.model.member.CreateMemberWebResponse;
import com.gdn.blibli.library.web.model.member.UpdateMemberWebRequest;
import com.gdn.blibli.library.web.model.member.UpdateMemberWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Slf4j
@RestController
@RequestMapping(path = "/api/member",
    produces = MediaType.APPLICATION_JSON_VALUE)
@MandatoryParameterAtHeader
public class MemberController {

  @Autowired
  private CommandExecutor commandExecutor;

  @Autowired
  @Qualifier(value = "commandScheduler")
  private Scheduler scheduler;

  @PostHeaders
  public Mono<Response<CreateMemberWebResponse>> create(
      @RequestBody CreateMemberWebRequest createMemberWebRequest) {
    log.info("#createMember with request: {}", createMemberWebRequest);
    return this.commandExecutor
        .execute(CreateMemberCommand.class,
            CreateMemberCommandRequest.builder().memberId(createMemberWebRequest.getMemberId())
                .name(createMemberWebRequest.getName())
                .phoneNumber(createMemberWebRequest.getPhoneNumber()).build())
        .map(ResponseHelper::ok).subscribeOn(scheduler);
  }

  @PutHeaders
  public Mono<Response<UpdateMemberWebResponse>> update(
      @RequestParam String memberId,
      @RequestBody UpdateMemberWebRequest updateMemberWebRequest) {
    log.info("#updateMember with request: {}", updateMemberWebRequest);
    return this.commandExecutor
        .execute(UpdateMemberCommand.class,
            UpdateMemberCommandRequest.builder().memberId(memberId)
                .name(updateMemberWebRequest.getName())
                .phoneNumber(updateMemberWebRequest.getPhoneNumber()).build())
        .map(ResponseHelper::ok).subscribeOn(scheduler);
  }
}
