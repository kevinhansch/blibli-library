package com.gdn.blibli.library.command.model.book;

import com.gdn.blibli.library.enums.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindBookByFilterCommandRequest {
  private String bookCode;
  private String title;
  private String author;
  private String publisher;
  private BookStatus bookStatus;
  private Long page;
  private Long size;
}
