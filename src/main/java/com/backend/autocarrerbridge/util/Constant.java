package com.backend.autocarrerbridge.util;



public class Constant {
    // Mã trạng thái HTTP
    public static final int SUCCESS = 200;
    public static final int NO_CONTENT = 204;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;
    public static final int INTERNAL_SERVER_ERROR = 500;

    // Khác
    public static final Integer TIME_TO_LIVE = 1440;

    // Phân quyền
    public static final String DESCRIPTION_ADMIN = "Quản trị viên";
    public static final String DESCRIPTION_BUSINESS = "Doanh nghiệp";
    public static final String DESCRIPTION_UNIVERSITY = "Trường học";
    public static final String DESCRIPTION_EMPLOYEE = "Nhân viên";
    public static final String DESCRIPTION_SUB_ADMIN = "Quản trị viên con";

    // Thông báo phản hồi
    public static final String NOTIFICATION_NEW_PW = "Mật khẩu mới đã được gửi đến email của bạn";
    public static final String NOTIFICATION_PW_WAIT = "Mật khẩu mới đang được xử lý, vui lòng kiểm tra email";
    public static final String SUCCESS_MESSAGE = "Thành công";
    public static final String NO_CONTENT_MESSAGE = "Không có nội dung";
    public static final String BAD_REQUEST_MESSAGE = "Yêu cầu không hợp lệ";
    public static final String NOT_FOUND_MESSAGE = "Không tìm thấy tài nguyên";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Lỗi máy chủ nội bộ";
    public static final String VALIDATION_ERROR_MESSAGE = "Lỗi xác thực";
    public static final String NOTIFICATION_WAIT = "Vui lòng chờ";

    // Thông báo khác
    public static final String UNAUTHORIZED_MESSAGE = "Truy cập không được phép";
    public static final String FORBIDDEN_MESSAGE = "Truy cập bị từ chối";
    public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Dữ liệu không thể xử lý";
    public static final String CONFLICT_MESSAGE = "Xung đột xảy ra";

    // Thông báo lỗi cụ thể
    public static final String ERROR_INVALID_EMAIL = "Email không hợp lệ.";
    public static final String ERROR_EXIST = "Email đã tồn tại.";
    public static final String ERROR_NOT_FOUND = "Không tìm thấy email.";
    public static final String ERROR_USER_EXIST = "Tên người dùng đã tồn tại.";
    public static final String PW_NOT_MATCH = "Mật khẩu không khớp.";
    public static final String ERROR_INVALID_PHONE_NUMBER = "Số điện thoại không hợp lệ.";
    public static final String USER_NOT_FOUND = "Không tìm thấy người dùng.";
    public static final String LICENSE_INVALID = "Giấy phép không hợp lệ.";
    public static final String ERROR_PW = "Mật khẩu không chính xác.";
    public static final String ERROR_PW_CHECK = "Mật khẩu mới không được trùng với mật khẩu cũ.";
    public static final String ERROR_PHONE = "Số điện thoại đã tồn tại.";
    public static final String USER_PENDING = "Người dùng đang chờ phê duyệt, vui lòng đợi.";
    public static final String ERROR_NOT_MATCH_CODE = "Mã không khớp.";
    public static final String ERROR_USER_EXISTED = "Người dùng đã tồn tại.";
    public static final String ERROR_TOKEN_INVALID_MESSAGE = "Token không hợp lệ.";
    public static final String ERROR_APPROVED = "Người dùng đã được phê duyệt.";

    // Lỗi liên quan đến hình ảnh
    public static final String EMPTY_FILE_MESSAGE = "Tệp rỗng.";
    public static final String TYPE_FILE_MESSAGE = "Loại tệp không hợp lệ.";
    public static final String LIMIT_SIZE_FILE_MESSAGE = "Kích thước tệp vượt quá giới hạn cho phép.";
    public static final String DIRECTORY_FILE_MESSAGE = "Không thể tạo thư mục.";
    public static final String SAVE_FILE_MESSAGE = "Không thể lưu tệp.";
    public static final String OPEN_IMAGE_MESSAGE = "Không thể mở hình ảnh.";
    public static final String NOT_FOUND_IMAGE_MESSAGE = "Không tìm thấy hình ảnh.";
    public static final String DELETE_IMAGE_MESSAGE = "Không thể xóa tệp khỏi bộ nhớ.";
    public static final String NOT_FOUND_FILE_IMAGE_MESSAGE = "Không tìm thấy tệp trong bộ nhớ.";

    // Thông báo lỗi Sub-admin
    public static final String NOT_FOUND_SUB_ADMIN = "Không tìm thấy sub-admin theo id.";
    public static final String NOT_FOUND_ROLE = "Không tìm thấy vai trò.";
    public static final String NOT_FOUND_BUSINESS_MESSAGE = "Không tìm thấy doanh nghiệp.";
    public static final String NOT_FOUND_UNIVERSITY_MESSAGE = "Không tìm thấy trường đại học.";
    public static final String INVALID_LENGTH_PW_MESSAGE = "Độ dài mật khẩu không hợp lệ.";
    public static final String MIN_LENGTH_PW_MESSAGE =
            "Tổng độ dài các ký tự bắt buộc lớn hơn độ dài tối thiểu.";

    // Ngành
    public static final String EXIST_NAME = "Tên ngành đã tồn tại.";
    public static final String EXIST_CODE = "Mã ngành đã tồn tại.";
    public static final String EXIST_NAME_AND_CODE = "Tên ngành hoặc mã ngành đã tồn tại.";
    public static final String NO_CHANGES_DETECTED = "Không có thay đổi nào.";
    public static final String DELETED = "Xóa thành công.";

    // Token - JWT
    public static final String JTI = "jti";
    public static final String TOKEN_BLACKLIST = "Token đã bị xoá!";
    public static final String DEFAULT_USERNAME = "admin";
    public static final String SUB = "sub";
    public static final String PREFIX_NP = "/np";
    public static final String PREFIX_FG = "/fg";
    public static final String CREATE_NEW_TOKEN = "Tạo token mới thành công";
    public static final String APPLICATION_NAME = "AutoCareer";
    public static final String SCOPE = "scope";


    // Tài khoản
    public static final String LOGIN_SUCCESS = "Đăng nhập thành công";
    public static final String LOGOUT_SUCCESS = "Đăng xuất thành công";
    public static final String SEND_CODE = "Mã code đã được gửi về email của bạn";
    public static final String CHANGE_PW = "Đổi mật khẩu thành công";
    public static final String ACCEPT_NP = " Xác nhận mật khẩu mới";
    public static final String ACCEPT_US = " Xác nhận đăng ký tài khoản";
    public static final String NEW_CODE = "Mã cấp mật khẩu mới!";
    // Doanh nghiệp
    public static final String REGISTER_BUSINESS = "Đăng ký tài khoản doanh nghiệp thành công, vui lòng đợi hệ thống duyệt";

    // Trường đại học
    public static final String REGISTER_UNIVERSITY = "Đăng ký tài khoản trường đại học thành công, vui lòng đợi hệ thống duyệt";

    //Employee
    public static final String NAME_NOT_BLANK_MESSAGE = "Name cannot be blank";
    public static final String GENDER_NOT_BLANK_MESSAGE = "Gender cannot be blank";
    public static final String DOB_NOT_BLANK_MESSAGE = "Date of birth cannot be blank";
    public static final String EMAIL_NOT_BLANK_MESSAGE = "Email cannot be blank";
    public static final String PHONE_NOT_BLANK_MESSAGE = "Phone can't not be blank";
    public static final String COED_EMPLOYEE_NOT_BLANK_MESSAGE = "Name cannot be blank";
//    public static final String NAME_NOT_BLANK = "Name cannot be blank";
//    public static final String NAME_NOT_BLANK = "Name cannot be blank";
//    public static final String NAME_NOT_BLANK = "Name cannot be blank";
//    public static final String NAME_NOT_BLANK = "Name cannot be blank";
//    public static final String NAME_NOT_BLANK = "Name cannot be blank";
//    public static final String NAME_NOT_BLANK = "Name cannot be blank";
}
