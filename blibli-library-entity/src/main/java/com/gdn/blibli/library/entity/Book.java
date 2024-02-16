package com.gdn.blibli.library.entity;

import com.gdn.blibli.library.constant.BookStatus;
import com.gdn.blibli.library.constant.CollectionName;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = CollectionName.BOOK)
public class Book extends BaseEntity {
  private String code;
  private String title;
  private String author;
  private String publisher;
  private BookStatus status;
  private List<BorrowHistory> history;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class BorrowHistory {
    private String memberId;
    private Date borrowDate;
  }
}
