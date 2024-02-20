package com.gdn.blibli.library.web.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookWebRequest implements Serializable {
  private static final long serialVersionUID = -7927624249240560500L;

  private String code;
  private String title;
  private String author;
  private String publisher;
}
