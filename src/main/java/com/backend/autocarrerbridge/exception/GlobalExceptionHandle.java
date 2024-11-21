package com.backend.autocarrerbridge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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

    //  @ExceptionHandler(value = MethodArgumentNotValidException.class)
    //  ResponseEntity<ApiException> handleArgumentNotValidException(
    //      MethodArgumentNotValidException exception) {
    //    String enumkey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
    //    ErrorCode errorCode = ErrorCode.valueOf(enumkey);
    //
    //    ApiException apiException = new ApiException();
    //    apiException.setCode(errorCode.getCode());
    //    apiException.setMessage(errorCode.getMessage());
    //    return ResponseEntity.status(errorCode.getHttpStatus()).body(apiException);
    //  }
    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<ApiException> handleIllegalArgumentException(IllegalArgumentException exception) {
        String message = exception.getMessage();
        ErrorCode errorCode = ErrorCode.valueOf(message);

        ApiException apiException = new ApiException();
        apiException.setCode(errorCode.getCode());
        apiException.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiException);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Lấy lỗi đầu tiên từ danh sách lỗi
        FieldError fieldError =
                (FieldError) ex.getBindingResult().getAllErrors().get(0);
        String errorMessage = fieldError.getDefaultMessage(); // Lấy thông báo lỗi

        // Trả về thông báo lỗi duy nhất
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiException().setCode(404).setMessage(errorMessage));
    }
}
