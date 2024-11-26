package com.backend.autocarrerbridge.exception;

import static com.backend.autocarrerbridge.util.Constant.*;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /** Không có nội dung hiển thị */
    ERROR_CODE_NOT_FOUND(NOT_FOUND, NO_CONTENT_MESSAGE, HttpStatus.NOT_FOUND),

    // Ngành nghề
    /** Tên hoặc mã của ngành đã tồn tại */
    ERROR_EXIST_NAME(BAD_REQUEST, EXIST_NAME, HttpStatus.BAD_REQUEST),
    ERROR_EXIST_CODE(BAD_REQUEST, EXIST_CODE, HttpStatus.BAD_REQUEST),
    /** Ngành nghề đã bị vô hiệu hoá từ trước*/
    ERROR_INACTIVE(BAD_REQUEST, INACTIVE, HttpStatus.BAD_REQUEST),
    ERROR_EXIST_NAME_AND_CODE(BAD_REQUEST, EXIST_NAME_AND_CODE, HttpStatus.BAD_REQUEST),
    ERROR_EXIST_INDUSTRY(BAD_REQUEST, NO_EXIST_INDUSTRY, HttpStatus.BAD_REQUEST),

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
    ERROR_EMAIL_NOT_FOUND(BAD_REQUEST, ERROR_NOT_FOUND, HttpStatus.BAD_REQUEST),
    ERROR_USER(BAD_REQUEST, ERROR_USER_EXIST, HttpStatus.BAD_REQUEST),
    ERROR_USER_NOT_FOUND(BAD_REQUEST, USER_NOT_FOUND, HttpStatus.BAD_REQUEST),
    ERROR_NO_CONTENT(BAD_REQUEST, NO_CONTENT_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_LICENSE(BAD_REQUEST, LICENSE_INVALID, HttpStatus.BAD_REQUEST),
    ERROR_PASSWORD_INCORRECT(BAD_REQUEST, ERROR_PW, HttpStatus.BAD_REQUEST),
    ERROR_PHONE_EXIST(BAD_REQUEST, ERROR_PHONE, HttpStatus.BAD_REQUEST),
    ERROR_USER_PENDING(BAD_REQUEST, USER_PENDING, HttpStatus.BAD_REQUEST),
    ERROR_VERIFY_CODE(BAD_REQUEST, ERROR_NOT_MATCH_CODE, HttpStatus.BAD_REQUEST),
    ERROR_USER_APPROVED(BAD_REQUEST, ERROR_APPROVED, HttpStatus.BAD_REQUEST),
    ERROR_PASSWORD_SAME(BAD_REQUEST, ERROR_PW_CHECK, HttpStatus.BAD_REQUEST),
    ERROR_USER_EXITED(BAD_REQUEST, ERROR_USER_EXISTED, HttpStatus.BAD_REQUEST),
    ERROR_PASSWORD_NOT_MATCH(BAD_REQUEST, PW_NOT_MATCH, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_ROLE(BAD_REQUEST, NOT_FOUND_ROLE, HttpStatus.BAD_REQUEST),
    ERROR_MIN_LENGTH_PASSWORD(BAD_REQUEST, MIN_LENGTH_PW_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_INVALID_LENGTH_PASSWORD(BAD_REQUEST, INVALID_LENGTH_PW_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_BUSINESS(BAD_REQUEST, NOT_FOUND_BUSINESS_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_UNIVERSITY(BAD_REQUEST, NOT_FOUND_UNIVERSITY_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_SUB_ADMIN_CODE_EXIST(BAD_REQUEST, "", HttpStatus.BAD_REQUEST),
    ERROR_UNIVERSITY_NOT_FOUND(BAD_REQUEST, NOT_FOUND_UNIVERSITY_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NAME(BAD_REQUEST, EXIST_NAME, HttpStatus.BAD_REQUEST),
    NOT_FOUNDED(BAD_REQUEST, EXIST_CODE, HttpStatus.BAD_REQUEST),
    ERROR_SECTION_NOT_FOUND(BAD_REQUEST, EXIST_NAME_AND_CODE, HttpStatus.BAD_REQUEST),
    ERROR_TOKEN_INVALID(BAD_REQUEST, ERROR_TOKEN_INVALID_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_EMAIL_REQUIRED(BAD_REQUEST, EMAIL_REQUIRED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_EMAIL_INVALID(BAD_REQUEST, EMAIL_INVALID_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NAME_INVALID(BAD_REQUEST, ERROR_NAME_EMPTY, HttpStatus.BAD_REQUEST),
    ERROR_TAX_CODE(BAD_REQUEST, TAX_CODE, HttpStatus.BAD_REQUEST),
    /** Các lỗi liên quan đến mã khôi phục */
    ERROR_PW_EMPTY(BAD_REQUEST, ERROR_NOT_EMPTY_PW, HttpStatus.BAD_REQUEST),
    ERROR_FORGOT_CODE_REQUIRED(BAD_REQUEST, FORGOT_CODE_REQUIRED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_FORGOT_CODE_INVALID(BAD_REQUEST, FORGOT_CODE_INVALID_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_FORGOT_CODE_EMPTY(BAD_REQUEST, FORGOT_CODE_EMPTY_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_TITLE_EMPTY(BAD_REQUEST, TITLE_WORK_SHOP_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DES_NULL(BAD_REQUEST, DES_WORK_SHOP_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DATE_EMPTY(BAD_REQUEST, DATE_WORK_SHOP_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_FAIL_WORK_SHOP(BAD_REQUEST, REQUEST_WORKSHOP_FAIL, HttpStatus.BAD_REQUEST),
    ERROR_WORK_SHOP_DATE(BAD_REQUEST, DATE_WORKSHOP, HttpStatus.BAD_REQUEST),

    //    validate @NotBlank Employee
    GENDER_NOT_BLANK(BAD_REQUEST, GENDER_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_NOT_BLANK(BAD_REQUEST, NAME_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    DOB_NOT_BLANK(BAD_REQUEST, DOB_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    EMAIL_NOT_BLANK(BAD_REQUEST, EMAIL_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    PHONE_NOT_BLANK(BAD_REQUEST, PHONE_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    CODE_EMPLOYEE_NOT_BLANK(BAD_REQUEST, CODE_EMPLOYEE_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    // validate section
    NAME_SECTION_NOT_BLANK(BAD_REQUEST, NAME_SECTION_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_SECTION_NOT_LOGGER(BAD_REQUEST, NAME_SECTION_NOT_LOGGER_MESSAGE, HttpStatus.BAD_REQUEST),
    DESCRIPTION(BAD_REQUEST, DESCRIPTION_MESSAGE, HttpStatus.BAD_REQUEST),
    STATUS_SECTION_NOT_NULL(BAD_REQUEST, STATUS_SECTION_NOT_NULL_MESSAGE, HttpStatus.BAD_REQUEST),
    CREATED_BY_SECTION_NOT_NULL(BAD_REQUEST, CREATED_BY_SECTION_NOT_NULL_MESSAGE, HttpStatus.BAD_REQUEST),
    UNIVERSITY_SECTION_NOT_NULL(BAD_REQUEST, UNIVERSITY_SECTION_NOT_NULL_MESSAGE, HttpStatus.BAD_REQUEST),
    SECTION_NOT_FOUND(BAD_REQUEST, SECTION_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
    SECTION_EXISTED(BAD_REQUEST, SECTION_EXISTED_MESSAGE, HttpStatus.BAD_REQUEST),
    // validation major
    CODE_MAJOR_NOT_BLANK(BAD_REQUEST, CODE_MAJOR_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    CODE_MAJOR_NOT_LOGGER(BAD_REQUEST, NAME_MAJOR_NOT_LOGGER_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_MAJOR_NOT_BLANK(BAD_REQUEST, NAME_MAJOR_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_MAJOR_NOT_LOGGER(BAD_REQUEST, NAME_MAJOR_NOT_LOGGER_MESSAGE, HttpStatus.BAD_REQUEST),
    NUMBER_MIN_STUDENT_MAJOR(BAD_REQUEST, NUMBER_MIN_STUDENT_MAJOR_MESSAGE, HttpStatus.BAD_REQUEST),
    NUMBER_MAX_STUDENT_MAJOR(BAD_REQUEST, NUMBER_MAX_STUDENT_MAJOR_MESSAGE, HttpStatus.BAD_REQUEST),
    ID_MAJOR_NOT_NULL(BAD_REQUEST, ID_MAJOR_NOT_NULL_MESSAGE, HttpStatus.BAD_REQUEST),

    /** Các lỗi của job*/
    ERROR_NO_EDIT_JOB(BAD_REQUEST, NO_EDIT_JOB, HttpStatus.BAD_REQUEST),
    ERROR_NO_EXIST_JOB(BAD_REQUEST, NO_EXIST_JOB, HttpStatus.BAD_REQUEST),
    ERROR_ALREADY_INACTIVE(BAD_REQUEST, ALREADY_INACTIVE, HttpStatus.BAD_REQUEST),

    // Location
    ERROR_PROVINCE_NOT_FOUND(BAD_REQUEST, PROVINCE_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DISTRICT_NOT_FOUND(BAD_REQUEST, DISTRICT_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_WARD_NOT_FOUND(BAD_REQUEST, WARD_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),

    // Database
    FILED_DB_NOT_UNIQUE(BAD_REQUEST, FILED_DB_NOT_UNIQUE_MESSAGE, HttpStatus.BAD_REQUEST),
    ;

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public static ErrorCode fromMessage(String message) {
        return Arrays.stream(ErrorCode.values())
                .filter(errorCode -> errorCode.getMessage().equals(message))
                .findFirst()
                .orElse(ErrorCode.ERROR_CODE_NOT_FOUND);
    }
}
