package com.backend.autocarrerbridge.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.backend.autocarrerbridge.util.Constant.*;

@Getter
public enum ErrorCode {

    /**Not found */
    ERROR_CODE_NOT_FOUND(NOT_FOUND, NO_CONTENT_MESSAGE, HttpStatus.NOT_FOUND),

    /** Tên hoặc mã của ngành đã tồn tại */
    ERROR_EXiST_NAME(BAD_REQUEST, EXIST_NAME, HttpStatus.BAD_REQUEST),
    ERROR_EXiST_CODE(BAD_REQUEST, EXIST_CODE, HttpStatus.BAD_REQUEST),
    ERROR_EXiST_NAME_AND_CODE(BAD_REQUEST, EXIST_NAME_AND_CODE, HttpStatus.BAD_REQUEST),


    ERROR_EMPTY_FILE(BAD_REQUEST, EMPTY_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_TYPE_FILE(BAD_REQUEST, TYPE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_LIMIT_SIZE_FILE(BAD_REQUEST, LIMIT_SIZE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DIRECTORY_FILE(BAD_REQUEST, DIRECTORY_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_SAVE_FILE(BAD_REQUEST, SAVE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DELETE_IMAGE(BAD_REQUEST, DELETE_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_IMAGE(BAD_REQUEST, NOT_FOUND_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_FIND_IMAGE(BAD_REQUEST, FIND_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    NO_CHANGE_DETECTED(SUCCESS, NO_CHANGES_DETECTED, HttpStatus.OK),
    ERROR_OPEN_IMAGE(BAD_REQUEST, OPEN_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
