package com.gdn.blibli.library.command.impl.book;

import com.gdn.blibli.library.command.book.FindBookByFilterCommand;
import com.gdn.blibli.library.command.model.book.FindBookByFilterCommandRequest;
import com.gdn.blibli.library.repository.BookRepository;
import com.gdn.blibli.library.web.model.book.FindBookByFilterWebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FindBookByFilterCommandImpl implements FindBookByFilterCommand {

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Mono<FindBookByFilterWebResponse> execute(FindBookByFilterCommandRequest request) {
    return Mono.empty();
  }
}
