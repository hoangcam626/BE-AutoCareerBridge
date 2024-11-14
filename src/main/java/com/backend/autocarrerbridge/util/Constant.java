package com.backend.autocarrerbridge.util;

public class Constant {
  // Mã trạng thái HTTP
  public static final int SUCCESS = 200;
  public static final int NO_CONTENT = 204;
  public static final int BAD_REQUEST = 400;
  public static final int NOT_FOUND = 404;
  public static final int INTERNAL_SERVER_ERROR = 500;

  // Thông điệp phản hồi
  public static final String SUCCESS_MESSAGE = "Success";
  public static final String NO_CONTENT_MESSAGE = "No content available";
  public static final String BAD_REQUEST_MESSAGE = "Bad request";
  public static final String NOT_FOUND_MESSAGE = "Resource not found";
  public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
  public static final String VALIDATION_ERROR_MESSAGE = "Validation error";

  // Các thông điệp khác
  public static final String UNAUTHORIZED_MESSAGE = "Unauthorized access";
  public static final String FORBIDDEN_MESSAGE = "Access forbidden";
  public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Unprocessable entity";
  public static final String CONFLICT_MESSAGE = "Conflict occurred";

  // Các thông điệp cụ thể
  public static final String ERROR_INVALID_EMAIL = "Email không hợp lệ.";
  public static final String ERROR_INVALID_PHONE_NUMBER = "Số điện thoại không hợp lệ.";
  public static final String ERROR_USER_NOT_FOUND = "Người dùng không tìm thấy.";

  //Các lỗi image
  public static final String EMPTY_FILE_MESSAGE= "Error: File is empty";
  public static final String TYPE_FILE_MESSAGE= "Error: Invalid file type";
  public static final String LIMIT_SIZE_FILE_MESSAGE= "Error: File size exceeds the allowed limit";
  public static final String DIRECTORY_FILE_MESSAGE= "Error: Failed to create directory.";
  public static final String SAVE_FILE_MESSAGE= "Error: Failed to save file.";
  public static final String OPEN_IMAGE_MESSAGE= "Error: can open image";
  public static final String FIND_IMAGE_MESSAGE= "Error: No image";
  public static final String DELETE_IMAGE_MESSAGE= "Error: Failed to delete file from storage";
  public static final String NOT_FOUND_IMAGE_MESSAGE="Error: File not found in storage";


}
