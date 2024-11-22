package com.backend.autocarrerbridge.exception;

import static com.backend.autocarrerbridge.util.Constant.*;

import org.springframework.http.HttpStatus;

import lombok.Getter;

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
    ERROR_FIND_IMAGE(BAD_REQUEST, NOT_FOUND_FILE_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    NO_CHANGE_DETECTED(SUCCESS, NO_CHANGES_DETECTED, HttpStatus.OK),
    ERROR_OPEN_IMAGE(BAD_REQUEST, OPEN_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_SUB_ADMIN(BAD_REQUEST, NOT_FOUND_SUB_ADMIN, HttpStatus.BAD_REQUEST),
    ERROR_VALID_EMAIL(BAD_REQUEST, ERROR_INVALID_EMAIL, HttpStatus.BAD_REQUEST),
    ERROR_EMAIL_EXIST(BAD_REQUEST, ERROR_EXIST, HttpStatus.BAD_REQUEST),
    ERROR_USER(BAD_REQUEST, ERROR_USER_EXIST, HttpStatus.BAD_REQUEST),
    ERROR_USER_NOT_FOUND(BAD_REQUEST, USER_NOT_FOUND, HttpStatus.BAD_REQUEST),
    ERROR_NO_CONTENT(BAD_REQUEST, NO_CONTENT_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_LICENSE(BAD_REQUEST, LICENSE_INVALID, HttpStatus.BAD_REQUEST),
    ERROR_PASSWORD_INCORRECT(BAD_REQUEST, ERROR_PASSWORD, HttpStatus.BAD_REQUEST),
    ERROR_PHONE_EXIST(BAD_REQUEST, ERROR_PHONE, HttpStatus.BAD_REQUEST),
    ERROR_USER_PENDING(BAD_REQUEST, USER_PENDING, HttpStatus.BAD_REQUEST),
    ERROR_VERIFY_CODE(BAD_REQUEST, ERROR_NOT_MATCH_CODE, HttpStatus.BAD_REQUEST),
    ERROR_PASSWORD_SAME(BAD_REQUEST, ERROR_PASSWORD_CHECK, HttpStatus.BAD_REQUEST),
    ERROR_USER_EXITED(BAD_REQUEST, ERROR_USER_EXISTED, HttpStatus.BAD_REQUEST),
    ERROR_PASSWORD_NOT_MATCH(BAD_REQUEST, PASSWORD_NOT_MATCH, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_ROLE(BAD_REQUEST, NOT_FOUND_ROLE, HttpStatus.BAD_REQUEST),
    ERROR_MIN_LENGTH_PASSWORD(BAD_REQUEST, MIN_LENGTH_PASSWORD_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_INVALID_LENGTH_PASSWORD(BAD_REQUEST, INVALID_LENGTH_PASSWORD_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_BUSINESS(BAD_REQUEST, NOT_FOUND_BUSINESS_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_UNIVERSITY(BAD_REQUEST, NOT_FOUND_UNIVERSITY_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_SUB_ADMIN_CODE_EXIST(BAD_REQUEST, "", HttpStatus.BAD_REQUEST),
    ERROR_TOKEN_INVALID(BAD_REQUEST, ERROR_TOKEN_INVALID_MESSAGE, HttpStatus.BAD_REQUEST),
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
