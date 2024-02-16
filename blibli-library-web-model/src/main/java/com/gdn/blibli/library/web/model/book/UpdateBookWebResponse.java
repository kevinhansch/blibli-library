package com.gdn.blibli.library.web.model.book;

import com.gdn.blibli.library.web.model.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookWebResponse extends BaseResponse {
  private static final long serialVersionUID = -3139075529985501938L;

  private String code;
  private String title;
  private String author;
  private String publisher;
  private String status;
}
