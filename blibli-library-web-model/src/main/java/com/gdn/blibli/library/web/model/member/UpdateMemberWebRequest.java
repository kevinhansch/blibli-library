package com.gdn.blibli.library.web.model.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberWebRequest implements Serializable {
  private static final long serialVersionUID = -5656493707312025160L;

  private String name;
  private String phoneNumber;
}
