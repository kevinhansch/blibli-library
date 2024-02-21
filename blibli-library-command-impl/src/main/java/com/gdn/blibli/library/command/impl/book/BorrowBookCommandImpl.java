package com.gdn.blibli.library.command.impl.book;

import com.gdn.blibli.library.command.book.BorrowBookCommand;
import com.gdn.blibli.library.command.model.book.BorrowBookCommandRequest;
import com.gdn.blibli.library.repository.BookRepository;
import com.gdn.blibli.library.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BorrowBookCommandImpl implements BorrowBookCommand {

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private BookRepository bookRepository;

  @Override
  public Mono<Boolean> execute(BorrowBookCommandRequest request) {
    return Mono.empty();
  }
}
