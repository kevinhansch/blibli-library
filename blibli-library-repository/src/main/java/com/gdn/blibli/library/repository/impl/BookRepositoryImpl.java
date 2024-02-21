package com.gdn.blibli.library.repository.impl;

import com.gdn.blibli.library.enums.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.repository.BookRepositoryCustom;

import reactor.core.publisher.Flux;

public class BookRepositoryImpl implements BookRepositoryCustom {

  @Autowired
  private ReactiveMongoTemplate mongoTemplate;

  @Override
  public Flux<Book> findByFilter(String bookCode, String title, String author, String publisher,
      BookStatus bookStatus, PageRequest pageRequest) {
    return null;
  }
}
