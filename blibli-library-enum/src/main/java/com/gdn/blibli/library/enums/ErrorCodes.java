package com.gdn.blibli.library.enums;

public enum ErrorCodes {

  OK(200, "OK", "OK"),
  DATA_NOT_FOUND(401, "DATA_NOT_FOUND", "Data Not Found");

  private int code;
  private String errorCode;
  private String message;

  ErrorCodes(int code, String errorCode, String message) {
    this.code = code;
    this.errorCode = errorCode;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getMessage() {
    return message;
  }

}
