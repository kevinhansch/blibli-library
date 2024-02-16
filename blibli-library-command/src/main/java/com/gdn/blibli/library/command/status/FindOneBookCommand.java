package com.gdn.blibli.library.command.status;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.command.model.book.FindOneBookCommandRequest;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;
import com.gdn.blibli.library.web.model.book.FindBookWebResponse;

public interface FindOneBookCommand
    extends Command<FindOneBookCommandRequest, FindBookWebResponse> {

}
