package com.gdn.blibli.library.entity;

import com.gdn.referral.service.constant.BookStatus;
import com.gdn.referral.service.constant.CollectionName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = CollectionName.BOOK)
public class Book extends BaseEntity {
  private String title;
  private String author;
  private String publisher;
  private BookStatus status;
}
