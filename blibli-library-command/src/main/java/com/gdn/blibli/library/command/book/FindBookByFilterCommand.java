package com.gdn.blibli.library.command.book;

import com.blibli.oss.backend.command.Command;
import com.gdn.blibli.library.command.model.book.FindBookByFilterCommandRequest;
import com.gdn.blibli.library.web.model.book.FindBookByFilterWebResponse;

public interface FindBookByFilterCommand
    extends Command<FindBookByFilterCommandRequest, FindBookByFilterWebResponse> {
}
