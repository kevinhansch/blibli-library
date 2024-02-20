package com.gdn.blibli.library.command.member;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.member.CreateMemberCommandRequest;
import com.gdn.blibli.library.web.model.member.CreateMemberWebResponse;

public interface CreateMemberCommand
    extends Command<CreateMemberCommandRequest, CreateMemberWebResponse> {

}
