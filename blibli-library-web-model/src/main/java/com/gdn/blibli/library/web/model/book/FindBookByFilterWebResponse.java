package com.gdn.blibli.library.web.model.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindBookByFilterWebResponse implements Serializable {
  private static final long serialVersionUID = -6129995242536849558L;

  private List<FindBookWebResponse> responses;
  private Long total;
}
