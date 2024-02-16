package com.gdn.blibli.library.command.status;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.book.FindAllBookCommandRequest;
import com.gdn.blibli.library.web.model.book.FindAllBookWebResponse;

public interface FindAllBookCommand
    extends Command<FindAllBookCommandRequest, FindAllBookWebResponse> {

}
