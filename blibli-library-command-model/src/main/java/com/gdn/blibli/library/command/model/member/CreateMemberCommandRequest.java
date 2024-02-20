package com.gdn.blibli.library.command.model.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberCommandRequest {
  private String memberId;
  private String name;
  private String phoneNumber;
}
