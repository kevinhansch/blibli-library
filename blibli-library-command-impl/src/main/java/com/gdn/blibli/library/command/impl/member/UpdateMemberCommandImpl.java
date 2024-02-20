package com.gdn.blibli.library.command.impl.member;

import com.gdn.blibli.library.command.member.UpdateMemberCommand;
import com.gdn.blibli.library.command.model.member.UpdateMemberCommandRequest;
import com.gdn.blibli.library.repository.MemberRepository;
import com.gdn.blibli.library.web.model.member.UpdateMemberWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UpdateMemberCommandImpl implements UpdateMemberCommand {

  @Autowired
  private MemberRepository memberRepository;

  @Override
  public Mono<UpdateMemberWebResponse> execute(UpdateMemberCommandRequest request) {
    return Mono.empty();
  }
}
