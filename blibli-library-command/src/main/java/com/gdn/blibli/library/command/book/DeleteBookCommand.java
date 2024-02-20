package com.gdn.blibli.library.command.book;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.book.DeleteBookCommandRequest;

public interface DeleteBookCommand
    extends Command<DeleteBookCommandRequest, Boolean> {

}
