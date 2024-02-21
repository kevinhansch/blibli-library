package com.gdn.blibli.library.command.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBookCommandRequest {
  private String memberId;
  private String bookCode;
}
