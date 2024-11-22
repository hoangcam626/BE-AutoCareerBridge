package com.backend.autocarrerbridge.util;

public class Constant {
    // HTTP Status Codes
    public static final int SUCCESS = 200;
    public static final int NO_CONTENT = 204;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;

    // Response Messages
    public static final String NOTIFICATION_NEW_PASSWORD = " Your new password is sent your mail";
    public static final String NOTIFICATION_WAIT = " Your new password is sent your mail, check it";
    public static final String SUCCESS_MESSAGE = "Success";
    public static final String NO_CONTENT_MESSAGE = "No content available";
    public static final String BAD_REQUEST_MESSAGE = "Bad request";
    public static final String NOT_FOUND_MESSAGE = "Resource not found";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation error";

    // Other Messages
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized access";
    public static final String FORBIDDEN_MESSAGE = "Access forbidden";
    public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Unprocessable entity";
    public static final String CONFLICT_MESSAGE = "Conflict occurred";

    // Specific Error Messages
    public static final String ERROR_INVALID_EMAIL = "Invalid email.";
    public static final String ERROR_EXIST = "Email already exists.";
    public static final String ERROR_USER_EXIST = "Username already exists.";
    public static final String PASSWORD_NOT_MATCH = "Passwords do not match";
    public static final String ERROR_INVALID_PHONE_NUMBER = "Invalid phone number.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String LICENSE_INVALID = "Invalid license.";
    public static final String ERROR_PASSWORD = "Incorrect password.";
    public static final String ERROR_PASSWORD_CHECK = "New password must not be the same as the old password.";
    public static final String ERROR_PHONE = "Phone already exists.";
    public static final String USER_PENDING = "User is pending, please wait.";
    public static final String ERROR_NOT_MATCH_CODE = "Code not match.";
    public static final String ERROR_USER_EXISTED = "User already existed.";

    // Image-related Errors
    public static final String EMPTY_FILE_MESSAGE = "File is empty";
    public static final String TYPE_FILE_MESSAGE = "Invalid file type";
    public static final String LIMIT_SIZE_FILE_MESSAGE = "File size exceeds the allowed limit";
    public static final String DIRECTORY_FILE_MESSAGE = "Failed to create directory";
    public static final String SAVE_FILE_MESSAGE = "Failed to save file";
    public static final String OPEN_IMAGE_MESSAGE = "Cannot open image";
    public static final String NOT_FOUND_IMAGE_MESSAGE = "No image found";
    public static final String DELETE_IMAGE_MESSAGE = "Failed to delete file from storage";
    public static final String NOT_FOUND_FILE_IMAGE_MESSAGE = "File not found in storage";
    // Sub-admin Error Messages
    public static final String NOT_FOUND_SUB_ADMIN = "Not found sub-admin by id";
    public static final String NOT_FOUND_ROLE = "Not found role";
    public static final String NOT_FOUND_BUSINESS_MESSAGE = "Not found business";
    public static final String NOT_FOUND_UNIVERSITY_MESSAGE = "Not found university";
    public static final String INVALID_LENGTH_PASSWORD_MESSAGE = "Invalid length parameters";
    public static final String MIN_LENGTH_PASSWORD_MESSAGE =
            "Combined minimum lengths preset character count are greater than the min length";
    // Industry
    public static final String EXIST_NAME = "Tên ngành đã tồn tại";
    public static final String EXIST_CODE = "Mã ngành đã tồn tại";
    public static final String EXIST_NAME_AND_CODE = "Tên ngành hoặc mã ngành đã tồn tại";
    public static final String NO_CHANGES_DETECTED = "Không có gì thay đổi";
    public static final String DELETED = "Xoá thành công";
}
