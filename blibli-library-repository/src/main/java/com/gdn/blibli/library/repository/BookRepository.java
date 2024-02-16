package com.gdn.blibli.library.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.gdn.blibli.library.entity.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository
    extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {

  Mono<Book> findByCode(String code);

  Flux<Book> findByIdNotNull(Pageable pageable);
}
