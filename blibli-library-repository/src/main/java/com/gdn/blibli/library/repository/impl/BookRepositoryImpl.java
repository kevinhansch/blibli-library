package com.gdn.blibli.library.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.repository.BookRepositoryCustom;

import reactor.core.publisher.Mono;

public class BookRepositoryImpl implements BookRepositoryCustom {

  @Autowired
  private ReactiveMongoTemplate mongoTemplate;

  @Override
  public Mono<Book> updateStatus(String id, String status) {
    return null;
  }

}
