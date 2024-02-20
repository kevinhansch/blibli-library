package com.gdn.blibli.library.command.member;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.member.UpdateMemberCommandRequest;
import com.gdn.blibli.library.web.model.member.UpdateMemberWebResponse;

public interface UpdateMemberCommand
    extends Command<UpdateMemberCommandRequest, UpdateMemberWebResponse> {

}
