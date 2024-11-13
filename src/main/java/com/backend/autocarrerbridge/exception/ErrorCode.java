package com.backend.autocarrerbridge.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  ;


  private final int code;
  private final String message;
  private final HttpStatus httpStatus;

  ErrorCode(int code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
