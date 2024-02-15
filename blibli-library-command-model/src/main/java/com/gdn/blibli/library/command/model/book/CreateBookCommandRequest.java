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
  private String variable;
  @NotBlank(message = "NotBlank")
  private String value;
  @NotBlank(message = "NotBlank")
  private String description;

}
