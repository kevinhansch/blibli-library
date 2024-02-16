package com.gdn.blibli.library.command.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookCommandRequest {

  @NotBlank(message = "NotBlank")
  private String code;
  @NotBlank(message = "NotBlank")
  private String title;
  @NotBlank(message = "NotBlank")
  private String author;
  @NotBlank(message = "NotBlank")
  private String publisher;
}
