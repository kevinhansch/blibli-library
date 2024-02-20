package com.gdn.blibli.library.web.model.member;

import com.gdn.blibli.library.web.model.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberWebResponse extends BaseResponse {
  private static final long serialVersionUID = -3139075529985501938L;

  private String memberId;
  private String name;
  private String phoneNumber;
}
