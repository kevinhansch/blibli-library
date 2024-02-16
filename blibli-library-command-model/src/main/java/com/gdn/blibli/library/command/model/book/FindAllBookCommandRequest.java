package com.gdn.blibli.library.command.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllBookCommandRequest {
  @NotNull(message = "NotNull")
  @Min(value = 1, message = "Min1")
  private Long page;

  @NotNull(message = "NotNull")
  @Min(value = 1, message = "Min1")
  private Long size;
}
