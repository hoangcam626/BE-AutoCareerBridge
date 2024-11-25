package com.backend.autocarrerbridge.exception;

import java.util.Objects;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiException> handleDepartmentException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiException apiException = new ApiException();
        apiException.setCode(errorCode.getCode());
        apiException.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiException);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiException> handleArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumkey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.fromMessage(enumkey);

        ApiException apiException = new ApiException();
        apiException.setCode(errorCode.getCode());
        apiException.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiException);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<ApiException> handleIllegalArgumentException(IllegalArgumentException exception) {
        String message = exception.getMessage();
        ErrorCode errorCode = ErrorCode.fromMessage(message);

        ApiException apiException = new ApiException();
        apiException.setCode(errorCode.getCode());
        apiException.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiException);
    }

}
