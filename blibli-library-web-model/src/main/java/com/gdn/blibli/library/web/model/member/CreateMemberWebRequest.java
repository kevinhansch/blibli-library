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
public class CreateMemberWebRequest implements Serializable {
  private static final long serialVersionUID = -1251574608566411902L;

  private String memberId;
  private String name;
  private String phoneNumber;
}
