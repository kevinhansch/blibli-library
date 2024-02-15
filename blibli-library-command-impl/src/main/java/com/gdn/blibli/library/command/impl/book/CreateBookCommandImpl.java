package com.gdn.blibli.library.command.impl.book;

import com.gdn.blibli.library.command.model.book.CreateBookCommandRequest;
import com.gdn.blibli.library.repository.BookRepository;
import com.gdn.blibli.library.web.model.book.CreateBookWebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdn.blibli.library.command.status.CreateBookCommand;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CreateBookCommandImpl implements CreateBookCommand {

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Mono<CreateBookWebResponse> execute(CreateBookCommandRequest request) {
    return null;
  }
}
