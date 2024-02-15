package com.gdn.blibli.library.command.model.book;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookCommandRequest {
  @NotBlank(message = "NotBlank")
  private String title;
  @NotBlank(message = "NotBlank")
  private String author;
  @NotBlank(message = "NotBlank")
  private String publisher;
}
