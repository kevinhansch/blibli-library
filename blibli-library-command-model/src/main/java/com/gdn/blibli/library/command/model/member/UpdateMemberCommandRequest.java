package com.gdn.blibli.library.command.model.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberCommandRequest {
  private String memberId;
  private String name;
  private String phoneNumber;
}
