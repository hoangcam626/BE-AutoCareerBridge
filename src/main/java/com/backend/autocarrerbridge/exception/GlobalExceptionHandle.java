package com.backend.autocarrerbridge.exception;

import java.util.Objects;


import com.backend.autocarrerbridge.util.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.backend.autocarrerbridge.model.api.ApiException;

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
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();

        // Sử dụng phương thức fromMessage để lấy ErrorCode từ enumKey (message)
        ErrorCode errorCode = ErrorCode.fromMessage(enumKey);

        ApiException apiException = new ApiException();
        apiException.setCode(errorCode.getCode());
        apiException.setMessage(errorCode.getMessage());

        // Trả về ResponseEntity với mã lỗi và thông điệp từ errorCode
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiException);
    }



    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<ApiException> handleIllegalArgumentException(IllegalArgumentException exception) {
        String message = exception.getMessage();
        ErrorCode errorCode = ErrorCode.valueOf(message);

        ApiException apiException = new ApiException();
        apiException.setCode(errorCode.getCode());
        apiException.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiException);
    }
}
