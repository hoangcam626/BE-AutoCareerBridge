package com.backend.autocarrerbridge.model.api;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;


import static com.backend.autocarrerbridge.util.Constant.*;

@Data
@Accessors(chain = true)
@Builder
public class ApiResponse<T> {
  private int code;
  private String message;
  public T data;

  public ApiResponse(){
    super();
    this.code = SUCCESS;
    this.message = SUCCESS_MESSAGE;
  }

  public ApiResponse(int code, String message, T data) {
    super();
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public ApiResponse(T data) {
    super();
    this.code = SUCCESS;
    this.message = SUCCESS_MESSAGE;
    this.data = data;
  }
}
