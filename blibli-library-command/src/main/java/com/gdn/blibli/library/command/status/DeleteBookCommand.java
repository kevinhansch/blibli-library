package com.gdn.blibli.library.command.status;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.command.model.book.DeleteBookCommandRequest;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;

public interface DeleteBookCommand
    extends Command<DeleteBookCommandRequest, Boolean> {

}
