package com.backend.autocarrerbridge.dto;

import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS_MESSAGE;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
public class ApiResponse<T> {
    @Builder.Default
    private int code = SUCCESS;

    @Builder.Default
    private String message = SUCCESS_MESSAGE;

    public T data;

    public ApiResponse() {
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
