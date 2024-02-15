package com.gdn.blibli.library.repository;

import com.gdn.blibli.library.entity.Book;

import reactor.core.publisher.Mono;

public interface BookRepositoryCustom {

  Mono<Book> updateStatus(String id, String status);
}
