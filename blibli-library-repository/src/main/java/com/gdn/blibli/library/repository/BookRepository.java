package com.gdn.blibli.library.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.gdn.blibli.library.entity.Book;

public interface BookRepository
    extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {

}
