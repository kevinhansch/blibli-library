package com.gdn.blibli.library.web.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllBookWebResponse {
  private List<FindBookWebResponse> responses;
  private Long total;
}
