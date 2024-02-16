package com.gdn.blibli.library.command.status;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.command.model.book.UpdateBookCommandRequest;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;
import com.gdn.blibli.library.web.model.book.UpdateBookWebResponse;

public interface UpdateBookCommand
    extends Command<UpdateBookCommandRequest, UpdateBookWebResponse> {

}
