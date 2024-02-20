package com.gdn.blibli.library.command.impl.member;

import com.gdn.blibli.library.command.member.CreateMemberCommand;
import com.gdn.blibli.library.command.model.member.CreateMemberCommandRequest;
import com.gdn.blibli.library.repository.MemberRepository;
import com.gdn.blibli.library.web.model.member.CreateMemberWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CreateMemberCommandImpl implements CreateMemberCommand {

  @Autowired
  private MemberRepository memberRepository;

  @Override
  public Mono<CreateMemberWebResponse> execute(CreateMemberCommandRequest request) {
    return Mono.empty();
  }
}
