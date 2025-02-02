package com.backend.autocarrerbridge.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;

import lombok.Getter;

import static com.backend.autocarrerbridge.util.Constant.ACCOUNT_ALREADY_APPROVED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ADDRESS_DESCRIPTION;
import static com.backend.autocarrerbridge.util.Constant.ADDRESS_INVALID_SPACE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ADDRESS_NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.ALREADY_INACTIVE;
import static com.backend.autocarrerbridge.util.Constant.APPROVED_RELATION;
import static com.backend.autocarrerbridge.util.Constant.CANCEL_RELATION;
import static com.backend.autocarrerbridge.util.Constant.CANCEL_WORK_SHOP;
import static com.backend.autocarrerbridge.util.Constant.CODE_EXIT_SUB_ADMIN_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.CODE_MAJOR_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.CONTENT_TOO_LONG_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DATE_WORKSHOP_OUT_DATE;
import static com.backend.autocarrerbridge.util.Constant.DISTRICT_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DISTRICT_NOT_FOUND_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMAIL_INVALID_SPACE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_FROM_GREAT_TO_SALARY;
import static com.backend.autocarrerbridge.util.Constant.ERROR_INVALID_PHONE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_INVALID_PHONE_NUMBER;
import static com.backend.autocarrerbridge.util.Constant.ERROR_LIST_EMPTY_MESAGE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_PW_INCORRECT_FORMAT;
import static com.backend.autocarrerbridge.util.Constant.ERROR_TAX_CODE_EXIST;
import static com.backend.autocarrerbridge.util.Constant.FILED_DB_NOT_UNIQUE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.FOUNDED_YEAR_UNIVERSITY_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.HAVE_RELATION;
import static com.backend.autocarrerbridge.util.Constant.ID_MAJOR_NOT_NULL_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_EXIST_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_ACCOUNT_STATE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ACCOUNT_ALREADY_REJECTED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ACCOUNT_IS_NULL;
import static com.backend.autocarrerbridge.util.Constant.BAD_REQUEST;
import static com.backend.autocarrerbridge.util.Constant.CODE_EMPLOYEE_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.CREATED_BY_SECTION_NOT_NULL_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DATE_WORKSHOP;
import static com.backend.autocarrerbridge.util.Constant.DATE_WORK_SHOP_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DELETE_IMAGE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DESCRIPTION_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DES_WORK_SHOP_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DIRECTORY_FILE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.DOB_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMAIL_INVALID_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMAIL_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMAIL_REQUIRED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EMPTY_FILE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_APPROVED;
import static com.backend.autocarrerbridge.util.Constant.ERROR_EXIST;
import static com.backend.autocarrerbridge.util.Constant.ERROR_INVALID_EMAIL;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NAME_EMPTY;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NOT_EMPTY_PW;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.ERROR_NOT_MATCH_CODE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_PHONE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_PW;
import static com.backend.autocarrerbridge.util.Constant.ERROR_PW_CHECK;
import static com.backend.autocarrerbridge.util.Constant.ERROR_TOKEN_INVALID_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.ERROR_USER_EXIST;
import static com.backend.autocarrerbridge.util.Constant.ERROR_USER_EXISTED;
import static com.backend.autocarrerbridge.util.Constant.EXIST_CODE;
import static com.backend.autocarrerbridge.util.Constant.EXIST_INDUSTRY_OF_BUSINESS;
import static com.backend.autocarrerbridge.util.Constant.EXIST_NAME;
import static com.backend.autocarrerbridge.util.Constant.EXIST_NAME_AND_CODE;
import static com.backend.autocarrerbridge.util.Constant.FORGOT_CODE_EMPTY_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.FORGOT_CODE_INVALID_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.FORGOT_CODE_REQUIRED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.GENDER_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INACTIVE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_EXPIRED_DATE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_EXPIRED_DATE_FUTRURE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_JOB_STATE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_LENGTH_PW_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_SALARY;
import static com.backend.autocarrerbridge.util.Constant.INVALID_TITLE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_TITLE_OUT;
import static com.backend.autocarrerbridge.util.Constant.INVALID_NAME_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INVALID_WORKSHOP_STATE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.JOB_ALREADY_APPROVED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.JOB_ALREADY_REJECTED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.LICENSE_INVALID;
import static com.backend.autocarrerbridge.util.Constant.LIMIT_SIZE_FILE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.LOCATION_NOT_FOUND_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.LOCK_SIGN_IN;
import static com.backend.autocarrerbridge.util.Constant.MIN_LENGTH_PW_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_MAJOR_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_MAJOR_NOT_LOGGER_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_REGISTER;
import static com.backend.autocarrerbridge.util.Constant.NAME_SECTION_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_SECTION_NOT_LOGGER_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_UNIVERSITY_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NAME_UNIVERSITY_SIZE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NOTIFICATION_ALREADY_READ_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NOTIFICATION_NOT_FOUND_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.NOT_FOUND_BUSINESS_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NOT_FOUND_FILE_IMAGE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NOT_FOUND_IMAGE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NOT_FOUND_ROLE;
import static com.backend.autocarrerbridge.util.Constant.NOT_FOUND_SUB_ADMIN;
import static com.backend.autocarrerbridge.util.Constant.NOT_FOUND_UNIVERSITY_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NO_CHANGES_DETECTED;
import static com.backend.autocarrerbridge.util.Constant.NO_CODE_SUB_ADMIN;
import static com.backend.autocarrerbridge.util.Constant.NO_CONTENT_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NO_EDIT_JOB;
import static com.backend.autocarrerbridge.util.Constant.NO_EXIST_INDUSTRY;
import static com.backend.autocarrerbridge.util.Constant.NO_EXIST_JOB;
import static com.backend.autocarrerbridge.util.Constant.NO_EXIST_WORKSHOP;
import static com.backend.autocarrerbridge.util.Constant.NO_PENDING_UNIVERSITY_JOB;
import static com.backend.autocarrerbridge.util.Constant.NUMBER_MAX_STUDENT_MAJOR_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NUMBER_MIN_STUDENT_MAJOR_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.OPEN_IMAGE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PHONE_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PHONE_UNIVERSITY_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PROVINCE_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PROVINCE_NOT_FOUND_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.PW_NOT_MATCH;
import static com.backend.autocarrerbridge.util.Constant.PW_REGISTER;
import static com.backend.autocarrerbridge.util.Constant.REJECTED_RELATION;
import static com.backend.autocarrerbridge.util.Constant.REQUEST_ALREADY_ACCEPT;
import static com.backend.autocarrerbridge.util.Constant.REQUEST_WORKSHOP_FAIL;
import static com.backend.autocarrerbridge.util.Constant.SAVE_FILE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.SECTION_EXISTED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.SECTION_HAVE_ACTIVE_MAJOR_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.SECTION_HAVE_MAJOR_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.SECTION_INACTIVE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.SECTION_NOT_FOUND_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.SENDED_FORGOT_PASS;
import static com.backend.autocarrerbridge.util.Constant.STATUS_SECTION_NOT_NULL_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.SUCCESS;
import static com.backend.autocarrerbridge.util.Constant.TAX_CODE;
import static com.backend.autocarrerbridge.util.Constant.TAX_CODE_REGISTER;

import static com.backend.autocarrerbridge.util.Constant.TITLE_SIZE_MAX;
import static com.backend.autocarrerbridge.util.Constant.TITLE_SIZE_MIN;
import static com.backend.autocarrerbridge.util.Constant.TITLE_WORK_SHOP_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TOO_LONG_ADDRESS_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TOO_LONG_EMAIL_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TOO_LONG_NAME_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.TYPE_FILE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.UNIVERSITY_JOB_EXITS;
import static com.backend.autocarrerbridge.util.Constant.UNIVERSITY_JOB_NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.UNIVERSITY_SECTION_NOT_NULL_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.USER_NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.USER_PENDING;
import static com.backend.autocarrerbridge.util.Constant.WARD_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.WARD_NOT_FOUND_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.WEBSITE_UNIVERSITY_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.WORKSHOP_ALREADY_APPROVED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.WORKSHOP_ALREADY_REJECTED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.EXIST_INDUSTRY_OF_JOB;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_ADDRESS_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_CODE_EXISTED_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_EMAIL_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_EMAIL_REGEX_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_GENDER_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_GENDER_REGEX_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_NAME_NOT_BANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_NAME_SIZE_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.INSTRUCTIONAL_PHONE_NOT_BLANK_MESSAGE;
import static com.backend.autocarrerbridge.util.Constant.NO_INACTIVE_JOB;



@Getter
public enum ErrorCode {

    /** Không có nội dung hiển thị */
    ERROR_CODE_NOT_FOUND(NOT_FOUND, NO_CONTENT_MESSAGE, HttpStatus.NOT_FOUND),
    ERROR_LIST_EMPTY(BAD_REQUEST,ERROR_LIST_EMPTY_MESAGE,HttpStatus.BAD_REQUEST),

    // Ngành nghề
    /** Tên hoặc mã của ngành đã tồn tại */
    ERROR_EXIST_NAME(BAD_REQUEST, EXIST_NAME, HttpStatus.BAD_REQUEST),
    ERROR_EXIST_CODE(BAD_REQUEST, EXIST_CODE, HttpStatus.BAD_REQUEST),
    /** Ngành nghề đã bị vô hiệu hoá từ trước*/
    ERROR_INACTIVE(BAD_REQUEST, INACTIVE, HttpStatus.BAD_REQUEST),
    ERROR_EXIST_NAME_AND_CODE(BAD_REQUEST, EXIST_NAME_AND_CODE, HttpStatus.BAD_REQUEST),
    ERROR_EXIST_INDUSTRY(BAD_REQUEST, NO_EXIST_INDUSTRY, HttpStatus.BAD_REQUEST),
    ERROR_EXIST_INDUSTRY_OF_BUSINESS(BAD_REQUEST, EXIST_INDUSTRY_OF_BUSINESS, HttpStatus.BAD_REQUEST),
    ERROR_CODE_INDUSTRY_IN_USE(BAD_REQUEST, EXIST_INDUSTRY_OF_JOB, HttpStatus.BAD_REQUEST),

    // Các lỗi liên quan đến image
    ERROR_EMPTY_FILE(BAD_REQUEST, EMPTY_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_TYPE_FILE(BAD_REQUEST, TYPE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_LIMIT_SIZE_FILE(BAD_REQUEST, LIMIT_SIZE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DIRECTORY_FILE(BAD_REQUEST, DIRECTORY_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_SAVE_FILE(BAD_REQUEST, SAVE_FILE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DELETE_IMAGE(BAD_REQUEST, DELETE_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_IMAGE(BAD_REQUEST, NOT_FOUND_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_FIND_IMAGE(BAD_REQUEST, NOT_FOUND_FILE_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_OPEN_IMAGE(BAD_REQUEST, OPEN_IMAGE_MESSAGE, HttpStatus.BAD_REQUEST),

    ERROR_WORK_SHOP_CANCEL(NOT_FOUND, CANCEL_WORK_SHOP, HttpStatus.NOT_FOUND),
    NO_CHANGE_DETECTED(SUCCESS, NO_CHANGES_DETECTED, HttpStatus.OK),
    ERROR_NO_CODE_SUB_ADMIN(BAD_REQUEST, NO_CODE_SUB_ADMIN, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_SUB_ADMIN(BAD_REQUEST, NOT_FOUND_SUB_ADMIN, HttpStatus.BAD_REQUEST),
    ERROR_VALID_EMAIL(BAD_REQUEST, ERROR_INVALID_EMAIL, HttpStatus.BAD_REQUEST),
    ERROR_VALID_PHONE(BAD_REQUEST, ERROR_INVALID_PHONE, HttpStatus.BAD_REQUEST),
    ERROR_EMAIL_EXIST(BAD_REQUEST, ERROR_EXIST, HttpStatus.BAD_REQUEST),
    ERROR_TAX_EXIST(BAD_REQUEST, ERROR_TAX_CODE_EXIST, HttpStatus.BAD_REQUEST),
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
    ERROR_SUB_ADMIN_CODE_EXIST(BAD_REQUEST, CODE_EXIT_SUB_ADMIN_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_UNIVERSITY_NOT_FOUND(BAD_REQUEST, NOT_FOUND_UNIVERSITY_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NAME(BAD_REQUEST, EXIST_NAME, HttpStatus.BAD_REQUEST),
    NOT_FOUNDED(BAD_REQUEST, EXIST_CODE, HttpStatus.BAD_REQUEST),
    ERROR_SECTION_NOT_FOUND(BAD_REQUEST, EXIST_NAME_AND_CODE, HttpStatus.BAD_REQUEST),
    ERROR_TOKEN_INVALID(BAD_REQUEST, ERROR_TOKEN_INVALID_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_EMAIL_REQUIRED(BAD_REQUEST, EMAIL_REQUIRED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_EMAIL_INVALID(BAD_REQUEST, EMAIL_INVALID_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NAME_INVALID(BAD_REQUEST, ERROR_NAME_EMPTY, HttpStatus.BAD_REQUEST),
    ERROR_TAX_CODE(BAD_REQUEST, TAX_CODE, HttpStatus.BAD_REQUEST),
    ERROR_ALREADY_ACCEPT(BAD_REQUEST, REQUEST_ALREADY_ACCEPT, HttpStatus.BAD_REQUEST),
    ERROR_FORMAT_PW(BAD_REQUEST, ERROR_PW_INCORRECT_FORMAT, HttpStatus.BAD_REQUEST),
    ERROR_TITLE_MAX(BAD_REQUEST, TITLE_SIZE_MAX, HttpStatus.BAD_REQUEST),
    ERROR_TITLE_MIN(BAD_REQUEST, TITLE_SIZE_MIN, HttpStatus.BAD_REQUEST),
    ERROR_ADDRESS_MAX(BAD_REQUEST, ADDRESS_DESCRIPTION, HttpStatus.BAD_REQUEST),
    ERROR_PW_REGISTER(BAD_REQUEST, PW_REGISTER, HttpStatus.BAD_REQUEST),
    ERROR_NAME_REGISTER(BAD_REQUEST, NAME_REGISTER, HttpStatus.BAD_REQUEST),
    ERROR_TAX_CODE_REGISTER(BAD_REQUEST, TAX_CODE_REGISTER, HttpStatus.BAD_REQUEST),
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
    ERROR_WORK_SHOP_DATE_OUT_DATE(BAD_REQUEST, DATE_WORKSHOP_OUT_DATE, HttpStatus.BAD_REQUEST),
    ERROR_FORGOT_EMAIL(BAD_REQUEST, SENDED_FORGOT_PASS, HttpStatus.BAD_REQUEST),
    ERROR_LENGTH_PW(BAD_REQUEST, ERROR_INVALID_PHONE_NUMBER, HttpStatus.BAD_REQUEST),
    LOCK_ERROR(BAD_REQUEST,LOCK_SIGN_IN, HttpStatus.BAD_REQUEST),

    //    validate  Employee
    GENDER_NOT_BLANK(BAD_REQUEST, GENDER_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_NOT_BLANK(BAD_REQUEST, NAME_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    DOB_NOT_BLANK(BAD_REQUEST, DOB_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    EMAIL_NOT_BLANK(BAD_REQUEST, EMAIL_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    PHONE_NOT_BLANK(BAD_REQUEST, PHONE_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    CODE_EMPLOYEE_NOT_BLANK(BAD_REQUEST, CODE_EMPLOYEE_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_TOO_LONG_EMAIL(BAD_REQUEST,TOO_LONG_EMAIL_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_TOO_LONG_NAME(BAD_REQUEST,TOO_LONG_NAME_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_INVALID_NAME(BAD_REQUEST, INVALID_NAME_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_ERROR_INVALID_PHONE_NUMBER(BAD_REQUEST, ERROR_INVALID_PHONE_NUMBER, HttpStatus.BAD_REQUEST),
    ERROR_TOO_LONG_ADDRESS(BAD_REQUEST,TOO_LONG_ADDRESS_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_EMAIL_INVALID_SPACE(BAD_REQUEST, EMAIL_INVALID_SPACE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_ADDRESS_INVALID_SPACE(BAD_REQUEST, ADDRESS_INVALID_SPACE_MESSAGE, HttpStatus.BAD_REQUEST),
    // validate section
    NAME_SECTION_NOT_BLANK(BAD_REQUEST, NAME_SECTION_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_SECTION_NOT_LOGGER(BAD_REQUEST, NAME_SECTION_NOT_LOGGER_MESSAGE, HttpStatus.BAD_REQUEST),
    DESCRIPTION(BAD_REQUEST, DESCRIPTION_MESSAGE, HttpStatus.BAD_REQUEST),
    STATUS_SECTION_NOT_NULL(BAD_REQUEST, STATUS_SECTION_NOT_NULL_MESSAGE, HttpStatus.BAD_REQUEST),
    CREATED_BY_SECTION_NOT_NULL(BAD_REQUEST, CREATED_BY_SECTION_NOT_NULL_MESSAGE, HttpStatus.BAD_REQUEST),
    UNIVERSITY_SECTION_NOT_NULL(BAD_REQUEST, UNIVERSITY_SECTION_NOT_NULL_MESSAGE, HttpStatus.BAD_REQUEST),
    SECTION_NOT_FOUND(BAD_REQUEST, SECTION_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
    SECTION_EXISTED(BAD_REQUEST, SECTION_EXISTED_MESSAGE, HttpStatus.BAD_REQUEST),
    SECTION_HAVE_MAJOR(BAD_REQUEST, SECTION_HAVE_MAJOR_MESSAGE, HttpStatus.BAD_REQUEST),
    SECTION_HAVE_ACTIVE_MAJOR(BAD_REQUEST,SECTION_HAVE_ACTIVE_MAJOR_MESSAGE,HttpStatus.BAD_REQUEST),
    // validation major
    CODE_MAJOR_NOT_BLANK(BAD_REQUEST, CODE_MAJOR_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    CODE_MAJOR_NOT_LOGGER(BAD_REQUEST, NAME_MAJOR_NOT_LOGGER_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_MAJOR_NOT_BLANK(BAD_REQUEST, NAME_MAJOR_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_MAJOR_NOT_LOGGER(BAD_REQUEST, NAME_MAJOR_NOT_LOGGER_MESSAGE, HttpStatus.BAD_REQUEST),
    NUMBER_MIN_STUDENT_MAJOR(BAD_REQUEST, NUMBER_MIN_STUDENT_MAJOR_MESSAGE, HttpStatus.BAD_REQUEST),
    NUMBER_MAX_STUDENT_MAJOR(BAD_REQUEST, NUMBER_MAX_STUDENT_MAJOR_MESSAGE, HttpStatus.BAD_REQUEST),
    ID_MAJOR_NOT_NULL(BAD_REQUEST, ID_MAJOR_NOT_NULL_MESSAGE, HttpStatus.BAD_REQUEST),
    SECTION_INACTIVE(BAD_REQUEST,SECTION_INACTIVE_MESSAGE,HttpStatus.BAD_REQUEST),

    // Các lỗi của job
    ERROR_NO_EDIT_JOB(BAD_REQUEST, NO_EDIT_JOB, HttpStatus.BAD_REQUEST),
    ERROR_NO_INACTIVE_JOB(BAD_REQUEST, NO_INACTIVE_JOB, HttpStatus.BAD_REQUEST),
    ERROR_NO_EXIST_JOB(BAD_REQUEST, NO_EXIST_JOB, HttpStatus.BAD_REQUEST),
    ERROR_ALREADY_INACTIVE(BAD_REQUEST, ALREADY_INACTIVE, HttpStatus.BAD_REQUEST),
    ERROR_TITLE(BAD_REQUEST, INVALID_TITLE, HttpStatus.BAD_REQUEST),
    ERROR_TITLE_OUT(BAD_REQUEST, INVALID_TITLE_OUT, HttpStatus.BAD_REQUEST),
    ERROR_SALARY(BAD_REQUEST, INVALID_SALARY, HttpStatus.BAD_REQUEST),
    ERROR_EXPIRED_DATE(BAD_REQUEST, INVALID_EXPIRED_DATE, HttpStatus.BAD_REQUEST),
    ERROR_EXPIRED_DATE_FUTRURE(BAD_REQUEST, INVALID_EXPIRED_DATE_FUTRURE, HttpStatus.BAD_REQUEST),
    ERROR_FROM_SALARY_TO(BAD_REQUEST, ERROR_FROM_GREAT_TO_SALARY, HttpStatus.BAD_REQUEST),

    // Database
    FILED_DB_NOT_UNIQUE(BAD_REQUEST, FILED_DB_NOT_UNIQUE_MESSAGE, HttpStatus.BAD_REQUEST),

    ERROR_ACCOUNT_ALREADY_APPROVED(BAD_REQUEST, ACCOUNT_ALREADY_APPROVED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_ACCOUNT_ALREADY_REJECTED(BAD_REQUEST, ACCOUNT_ALREADY_REJECTED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_INVALID_ACCOUNT_STATE(BAD_REQUEST, INVALID_ACCOUNT_STATE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_ACCOUNT_IS_NULL(BAD_REQUEST, ACCOUNT_IS_NULL, HttpStatus.BAD_REQUEST),
    ERROR_NOT_FOUND_ADDRESS(BAD_REQUEST, ADDRESS_NOT_FOUND, HttpStatus.BAD_REQUEST),

    // Các lỗi liên quan đến đơn vị hành chính
    ERROR_PROVINCE_NOT_FOUND(BAD_REQUEST, PROVINCE_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DISTRICT_NOT_FOUND(BAD_REQUEST, DISTRICT_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_WARD_NOT_FOUND(BAD_REQUEST, WARD_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_PROVINCE_NOT_BLANK(BAD_REQUEST, PROVINCE_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_DISTRICT_NOT_BLANK(BAD_REQUEST, DISTRICT_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_WARD_NOT_BLANK(BAD_REQUEST, WARD_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),

    ERROR_LOCATION_NOT_FOUND(BAD_REQUEST, LOCATION_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),

    // BusinessUniversity
    ERROR_EXIST_RELATION(BAD_REQUEST, HAVE_RELATION, HttpStatus.BAD_REQUEST),
    /**Đã hợp tác*/
    ERROR_APPROVED_RELATION(BAD_REQUEST, APPROVED_RELATION, HttpStatus.BAD_REQUEST),
    /**Yêu cầu bị từ chối*/
    ERROR_REJECTED_RELATION(BAD_REQUEST, REJECTED_RELATION, HttpStatus.BAD_REQUEST),
    /**Yêu cầu đã bị hủy trước đó*/
    ERROR_CANCEL_RELATION(BAD_REQUEST, CANCEL_RELATION, HttpStatus.BAD_REQUEST),

    // Validation university
    NAME_UNIVERSITY_NOT_BLANK(BAD_REQUEST, NAME_UNIVERSITY_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    NAME_UNIVERSITY_SIZE(BAD_REQUEST, NAME_UNIVERSITY_SIZE_MESSAGE, HttpStatus.BAD_REQUEST),
    WEBSITE_UNIVERSITY_NOT_BLANK(BAD_REQUEST, WEBSITE_UNIVERSITY_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    FOUNDED_YEAR_UNIVERSITY_NOT_BLANK(BAD_REQUEST, FOUNDED_YEAR_UNIVERSITY_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),
    PHONE_UNIVERSITY_NOT_BLANK(BAD_REQUEST, PHONE_UNIVERSITY_NOT_BLANK_MESSAGE, HttpStatus.BAD_REQUEST),

    //Validation giáo vụ
    INSTRUCTIONS_NOT_EXIST(BAD_REQUEST,INSTRUCTIONAL_EXIST_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_CODE_EXISTED(BAD_REQUEST,INSTRUCTIONAL_CODE_EXISTED_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_NAME_NOT_BANK(BAD_REQUEST,INSTRUCTIONAL_NAME_NOT_BANK_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_NAME_SIZE(BAD_REQUEST,INSTRUCTIONAL_NAME_SIZE_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_GENDER_NOT_BLANK(BAD_REQUEST,INSTRUCTIONAL_GENDER_NOT_BLANK_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_GENDER_REGEX(BAD_REQUEST,INSTRUCTIONAL_GENDER_REGEX_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_EMAIL_NOT_BLANK(BAD_REQUEST,INSTRUCTIONAL_EMAIL_NOT_BLANK_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_EMAIL_REGEX(BAD_REQUEST,INSTRUCTIONAL_EMAIL_REGEX_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_ADDRESS_NOT_BLANK(BAD_REQUEST, INSTRUCTIONAL_ADDRESS_NOT_BLANK_MESSAGE,HttpStatus.BAD_REQUEST),
    INSTRUCTIONAL_PHONE_NOT_BLANK(BAD_REQUEST,INSTRUCTIONAL_PHONE_NOT_BLANK_MESSAGE,HttpStatus.BAD_REQUEST),

    // Các lỗi duyệt thông tin
    ERROR_JOB_ALREADY_REJECTED(BAD_REQUEST, JOB_ALREADY_REJECTED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_JOB_ALREADY_APPROVED(BAD_REQUEST, JOB_ALREADY_APPROVED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_INVALID_JOB_STATE(BAD_REQUEST, INVALID_JOB_STATE_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NO_EXIST_WORKSHOP(BAD_REQUEST, NO_EXIST_WORKSHOP, HttpStatus.BAD_REQUEST),
    ERROR_WORKSHOP_ALREADY_APPROVED(BAD_REQUEST, WORKSHOP_ALREADY_APPROVED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_WORKSHOP_ALREADY_REJECTED(BAD_REQUEST, WORKSHOP_ALREADY_REJECTED_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_INVALID_WORKSHOP_STATE(BAD_REQUEST, INVALID_WORKSHOP_STATE_MESSAGE, HttpStatus.BAD_REQUEST),

    // Các lỗi thông báo
    ERROR_NOTIFICATION_NOT_FOUND(BAD_REQUEST, NOTIFICATION_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_NOTIFICATION_ALREADY_READ(BAD_REQUEST, NOTIFICATION_ALREADY_READ_MESSAGE, HttpStatus.BAD_REQUEST),
    ERROR_CONTENT_TOO_LONG(BAD_REQUEST,CONTENT_TOO_LONG_MESSAGE, HttpStatus.BAD_REQUEST),

    ERROR_UNIVERSITY_JOB_EXITS(BAD_REQUEST, UNIVERSITY_JOB_EXITS, HttpStatus.BAD_REQUEST),
    ERROR_UNIVERSITY_JOB_NOT_FOUND(BAD_REQUEST, UNIVERSITY_JOB_NOT_FOUND, HttpStatus.BAD_REQUEST),
    ERROR_NO_PENDING_UNIVERSITY_JOB(BAD_REQUEST, NO_PENDING_UNIVERSITY_JOB, HttpStatus.BAD_REQUEST),
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
