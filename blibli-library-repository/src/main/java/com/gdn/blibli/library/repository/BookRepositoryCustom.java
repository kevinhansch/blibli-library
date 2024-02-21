package com.gdn.blibli.library.repository;

import com.gdn.blibli.library.entity.Book;
import com.gdn.blibli.library.enums.BookStatus;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;

public interface BookRepositoryCustom {

  Flux<Book> findByFilter(String bookCode, String title, String author, String publisher,
      BookStatus bookStatus, PageRequest pageRequest);

}
