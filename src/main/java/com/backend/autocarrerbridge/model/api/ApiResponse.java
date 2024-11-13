package com.backend.autocarrerbridge.model.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ApiResponse<T> {
  private int code;
  private String message;
  public T data;

}
