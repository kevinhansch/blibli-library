package com.gdn.blibli.library.command.book;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.book.BorrowBookCommandRequest;
import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;

public interface BorrowBookCommand
    extends Command<BorrowBookCommandRequest, Boolean> {

}
