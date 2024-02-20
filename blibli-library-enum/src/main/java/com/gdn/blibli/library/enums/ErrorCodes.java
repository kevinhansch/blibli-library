package com.gdn.blibli.library.enums;

public enum ErrorCodes {

  OK(200, "OK", "OK"),
  DATA_NOT_FOUND(401, "DATA_NOT_FOUND", "Data Not Found"),
  MEMBER_NOT_FOUND(418, "MEMBER_NOT_FOUND", "Member not found"),
  BOOK_NOT_FOUND(418, "BOOK_NOT_FOUND", "Book not found"),
  BOOK_ALREADY_BORROWED(418, "BOOK_ALREADY_BORROWED", "Book already borrowed");

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
