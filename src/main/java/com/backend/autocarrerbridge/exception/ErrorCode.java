package com.backend.autocarrerbridge.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.backend.autocarrerbridge.util.Constant.*;


@Getter
public enum ErrorCode {

    ERROR_EMPTY_FILE(BAD_REQUEST, EMPTY_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_TYPE_FILE(BAD_REQUEST, TYPE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_LIMIT_SIZE_FILE(BAD_REQUEST, LIMIT_SIZE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DIRECTORY_FILE(BAD_REQUEST, DIRECTORY_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_SAVE_FILE(BAD_REQUEST, SAVE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DELETE_IMAGE(BAD_REQUEST, DELETE_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_IMAGE(BAD_REQUEST, NOT_FOUND_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_FIND_IMAGE(BAD_REQUEST, FIND_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_OPEN_IMAGE(BAD_REQUEST, OPEN_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_EMAIL_EXIST(BAD_REQUEST,ERROR_EXIST,HttpStatus.BAD_REQUEST),
    ERROR_USER(BAD_REQUEST, ERROR_USER_EXIST,HttpStatus.BAD_REQUEST),
    ERROR_USER_NOT_FOUND(BAD_REQUEST,USER_NOT_FOUND,HttpStatus.BAD_REQUEST),
    ERROR_NO_CONTENT(BAD_REQUEST,NO_CONTENT_MESSAGE,HttpStatus.BAD_REQUEST),
    ERROR_LINCESE(BAD_REQUEST,LICENSE_INVALID,HttpStatus.BAD_REQUEST),
    ERROR_PASSWORD_INCORRECT(BAD_REQUEST,ERROR_PASSWORD,HttpStatus.BAD_REQUEST),
    ERROR_PHONE_EXIST(BAD_REQUEST,ERROR_PHONE,HttpStatus.BAD_REQUEST),
    ERROR_USER_PENDING(BAD_REQUEST,USER_PENDING,HttpStatus.BAD_REQUEST),
    ERROR_PASSWORD_NOT_MATCH(BAD_REQUEST,PASSWORD_NOT_MATCH,HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
