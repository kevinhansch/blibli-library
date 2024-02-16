package com.gdn.blibli.library.exceptions;

import java.util.List;
import java.util.Map;

import com.gdn.blibli.library.enums.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 9049912945316290986L;

  private int code;
  private String errorCode;
  private String message;
  private Map<String, List<String>> errors;

  public BusinessException(ErrorCodes errorCodes) {
    this.code = errorCodes.getCode();
    this.errorCode = errorCodes.getErrorCode();
    this.message = errorCodes.getMessage();
  }

  public BusinessException(ErrorCodes errorCodes, Map<String, List<String>> errors) {
    this.code = errorCodes.getCode();
    this.errorCode = errorCodes.getErrorCode();
    this.message = errorCodes.getMessage();
    this.errors = errors;
  }
}
