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
    public static final String PREFIX_SUB_ADMIN_CODE = "ADMIN";

    // Phân quyền
    public static final String DESCRIPTION_ADMIN = "Quản trị viên";
    public static final String DESCRIPTION_BUSINESS = "Doanh nghiệp";
    public static final String DESCRIPTION_UNIVERSITY = "Trường học";
    public static final String DESCRIPTION_EMPLOYEE = "Nhân viên";
    public static final String DESCRIPTION_SUB_ADMIN = "Quản trị viên con";
    public static final String DESCRIPTION_INSTRUCTIONAL = "Giáo vụ";

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
    public static final String SUCCESS_ACCEPT_MESSAGE = "Chấp nhận yêu cầu thành công!";
    public static final String REJECT_ACCEPT_MESSAGE = "Từ chối thành công!";
    public static final String APPROVE_COOPERATION_MESSAGE= " đã chấp nhận yêu cầu hợp tác của bạn";
    public static final String REJECT_COOPERATION_MESSAGE= "đã từ chối yêu cầu hợp tác của bạn";
    public static final String CANCEL_WORK_SHOP = "Không thể huỷ hội thảo vì nó sắp diễn ra! Vui lòng liên hệ trường học!";
    // Thông báo khác
    public static final String UNAUTHORIZED_MESSAGE = "Truy cập không được phép";
    public static final String FORBIDDEN_MESSAGE = "Truy cập bị từ chối";
    public static final String UNPROCESSABLE_ENTITY_MESSAGE = "Dữ liệu không thể xử lý";
    public static final String CONFLICT_MESSAGE = "Xung đột xảy ra";
    public static final String DESCRIPTION_MESSAGE = "Mô tả không được dài quá 255 kí tự ";
    public static final String PREFIX_DATE = "Còn ";
    public static final String POSTFIX_DATE = " Ngày";

    // Thông báo lỗi cụ thể
    public static final String ERROR_INVALID_EMAIL = "Email không hợp lệ.";
    public static final String TOO_LONG_EMAIL_MESSAGE = "Email vượt quá 255 ký tự hoặc quá ngắn";
    public static final String ERROR_INVALID_PHONE = "số điện thoại không hợp lệ.";
    public static final String ERROR_EXIST = "Email đã tồn tại.";
    public static final String ERROR_TAX_CODE_EXIST = "Mã số thuế đã tồn tại";
    public static final String ERROR_NOT_FOUND = "Không tìm thấy email.";
    public static final String ERROR_USER_EXIST = "Tên người dùng đã tồn tại.";
    public static final String PW_NOT_MATCH = "Mật khẩu không khớp.";
    public static final String ERROR_INVALID_PHONE_NUMBER = "Số điện thoại gồm 10 số và bắt đầu bằng +84 hoặc 0 theo sau là số từ 3-9.";
    public static final String USER_NOT_FOUND = "Không tìm thấy người dùng.";
    public static final String LICENSE_INVALID = "Giấy phép không hợp lệ.";
    public static final String ERROR_PW = "Mật khẩu không chính xác.";
    public static final String ERROR_PW_CHECK = "Mật khẩu mới không được trùng với mật khẩu cũ.";
    public static final String ERROR_PHONE = "Số điện thoại đã tồn tại.";
    public static final String ERROR_NOT_EMPTY_PW = "Mật khẩu không dược để trống.";
    public static final String USER_PENDING = "Người dùng đang chờ phê duyệt, vui lòng đợi.";
    public static final String ERROR_NOT_MATCH_CODE = "Mã không khớp.";
    public static final String ERROR_USER_EXISTED = "Người dùng đã tồn tại.";
    public static final String ERROR_TOKEN_INVALID_MESSAGE = "Token không hợp lệ.";
    public static final String ERROR_APPROVED = "Người dùng đã được phê duyệt.";
    public static final String ERROR_NAME_EMPTY = "Tên không được để trống";
    public static final String ERROR_LIST_EMPTY_MESAGE = "Danh sách rỗng";
    public static final String ERROR_PW_INCORRECT_FORMAT = "Mật khẩu phải có ít nhất 8 ký tự 1 chữ hoa và một số và ký tự đặc biệt ";

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
    public static final String NO_CODE_SUB_ADMIN = "Mã quản trị viên không được để trống";
    public static final String NOT_FOUND_SUB_ADMIN = "Không tìm thấy sub-admin theo id.";
    public static final String NOT_FOUND_ROLE = "Không tìm thấy vai trò.";
    public static final String NOT_FOUND_BUSINESS_MESSAGE = "Không tìm thấy doanh nghiệp.";
    public static final String NOT_FOUND_UNIVERSITY_MESSAGE = "Không tìm thấy trường đại học.";
    public static final String INVALID_LENGTH_PW_MESSAGE = "Độ dài mật khẩu không hợp lệ.";
    public static final String MIN_LENGTH_PW_MESSAGE = "Tổng độ dài các ký tự bắt buộc lớn hơn độ dài tối thiểu.";
    public static final String CODE_EXIT_SUB_ADMIN_MESSAGE = "Mã nhân viên đã tồn tại.";
    // Ngành
    public static final String EXIST_NAME = "Tên ngành đã tồn tại.";
    public static final String EXIST_CODE = "Mã ngành đã tồn tại.";
    public static final String INACTIVE = "Không tìm thấy ngành nghề hoặc ngành nghề đã bị vô hiệu hoá";
    public static final String EXIST_NAME_AND_CODE = "Tên ngành hoặc mã ngành đã tồn tại.";
    public static final String NO_CHANGES_DETECTED = "Không có thay đổi nào.";
    public static final String DELETED = "Xóa thành công.";
    public static final String NO_EXIST_INDUSTRY = "Ngành không tồn tại";
    public static final String EXIST_INDUSTRY_OF_BUSINESS = "Doanh nghiệp đã có ngành nghề này";
    public static final String EXIST_INDUSTRY_OF_JOB = "Ngành nghề đang được sử dụng";
    public static final String INDUSTRY_IN_USE = "Xóa thành công ";
    public static final String INDUSTRY_IN_USE_ALL = "Ngành nghề đang được sử dụng, không thể xóa";

    // Token - JWT
    public static final String JTI = "jti";
    public static final String TOKEN_BLACKLIST = "Token đã bị xoá!";
    public static final String DEFAULT_USERNAME = "admin@domain.com";
    public static final String DEFAULT_PW = "admin";

    public static final String SUB = "sub";
    public static final String PREFIX_NP = "/np";
    public static final String PREFIX_FG = "/fg";
    public static final String CREATE_NEW_TOKEN = "Tạo token mới thành công";
    public static final String APPLICATION_NAME = "AutoCareer";
    public static final String SCOPE = "scope";

    // Email
    public static final String LOGIN_SUCCESS = "Đăng nhập thành công";
    public static final String LOGOUT_SUCCESS = "Đăng xuất thành công";
    public static final String SEND_CODE = "Mã code đã được gửi về email của bạn";
    public static final String CHANGE_PW = "Đổi mật khẩu thành công";
    public static final String ACCEPT_NP = " Xác nhận mật khẩu mới";
    public static final String ACCEPT_US = " Xác nhận đăng ký tài khoản";
    public static final String NEW_CODE = "Mã cấp mật khẩu mới!";
    public static final String ACCOUNT = "Tài Khoản Của Bạn";
    public static final String APPROVED_ACCOUNT = "Tài khoản của bạn đã được chấp nhận.";
    public static final String REJECTED_ACCOUNT = "Tài khoản của bạn đã bị từ chối.";
    public static final String APPROVED_WORKSHOP = "Tin hội thảo của bạn đã được phê duyệt.";
    public static final String REJECTED_WORKSHOP = "Tin hội thảo của bạn đã bị từ chối phê duyệt.";
    public static final String APPROVED_BUSINESS_WORKSHOP = "Yêu cầu tham gia hội thảo của bạn đã được phê duyệt.";
    public static final String REJECT_BUSINESS_WORKSHOP = "Yêu cầu tham gia hội thảo của bạn đã bị từ chối.";
    public static final String APPROVED_JOB = "Tin tuyển dụng của bạn đã được phê duyệt.";
    public static final String REJECTED_JOB = "Tin tuyển dụng của bạn đã bị từ chối phê duyệt.";
    public static final String CANCEL_BUSINESS_WORK_SHOP = "Vì một số lý do khách quan công ty huỷ bỏ tham gia hội thảo.";
    public static final String EMAIL_REQUIRED_MESSAGE = "Email không được để trống.";
    public static final String EMAIL_INVALID_MESSAGE = "Định dạng email không hợp lệ.";
    public static final String DELETE_WORK_SHOP = "Hội thảo đã bị huỷ!";

    public static final String FORGOT_CODE_REQUIRED_MESSAGE = "Mã khôi phục không được để trống.";
    public static final String FORGOT_CODE_INVALID_MESSAGE = "Mã khôi phục không hợp lệ.";
    public static final String FORGOT_CODE_EMPTY_MESSAGE = "Mã khôi phục không để trống.";
    public static final String ADDRESS_NOT_FOUND = "Địa chỉ không được để trống .";
    public static final String ACCOUNT_ALREADY_APPROVED_MESSAGE = "Tài khoản đã được chấp nhận.";
    public static final String ACCOUNT_ALREADY_REJECTED_MESSAGE = "Tài khoản đã bị từ chối.";
    public static final String INVALID_ACCOUNT_STATE_MESSAGE = "Trạng thái tài khoản không hợp lệ.";
    public static final String ACCOUNT_IS_NULL = "Tài khoản người dùng đang trống.";
    public static final String SENDED_FORGOT_PASS = "Mã đã được gửi vui lòng kiểm tra email.";

    // Doanh nghiệp
    public static final String REGISTER_BUSINESS =
            "Đăng ký tài khoản doanh nghiệp thành công, vui lòng đợi hệ thống duyệt";
    public static final String TAX_CODE = "Mã số thuế không được để trống";
    public static final String REQUEST_TO_ATTEND_WORKSHOP =
            "Yêu cầu tham gia hội thảo thành công, Vui lòng đợi để chờ duyệt.";
    public static final String REQUEST_WORKSHOP_FAIL = "Doanh nghiệp đã yêu cầu tham gia vui lòng đợi!.";
    public static final String REQUEST_ALREADY_ACCEPT = "Doanh nghiệp đã được duyệt!.";
    public static final String REQUEST_TAX_CODE_ALREADY = "Mã số thuế đã tòn tại!.";
    // Trường đại học
    public static final String REGISTER_UNIVERSITY =
            "Đăng ký tài khoản trường đại học thành công, vui lòng đợi hệ thống duyệt";
    public static final String TITLE_WORK_SHOP_MESSAGE = "Tiêu đề không để trống.";

    public static final String LOCK_SIGN_IN = "Tài khoản đã bị khóa tạm thời đăng nhập sai quá nhiều lần.";
    public static final String TITLE_SIZE_MAX = "Tiêu đề quá dài.";
    public static final String TITLE_SIZE_MIN = "Tiêu đề quá ngắn.";
    public static final String DATE_WORKSHOP = "Ngày bắt đầu không thể lớn hơn ngày kết thúc hoặc ngược lại.";
    public static final String DATE_WORKSHOP_OUT_DATE = "Hội thảo đã hết hạn.";
    public static final String DES_WORK_SHOP_MESSAGE = "Nội dung không thể rỗng.";
    public static final String DATE_WORK_SHOP_MESSAGE = "Ngày không được bỏ trống.";
    public static final String NAME_UNIVERSITY_NOT_BLANK_MESSAGE = "Tên trường đại học không được rỗng.";
    public static final String NAME_UNIVERSITY_SIZE_MESSAGE = "Tên trường đại học phải trong khoảng 10 đến 100 kí tự.";
    public static final String WEBSITE_UNIVERSITY_NOT_BLANK_MESSAGE = "Website không được bỏ trống.";
    public static final String FOUNDED_YEAR_UNIVERSITY_NOT_BLANK_MESSAGE = "Năm thành lập không được bỏ trống.";
    public static final String PHONE_UNIVERSITY_NOT_BLANK_MESSAGE = "Số điện thoại không được bỏ trống.";
    public static final String ADDRESS_DESCRIPTION = "Địa chỉ chi tiết quá dài!";
    public static final String NAME_REGISTER = "Tên đăng ký quá dài!";
    public static final String PW_REGISTER = "Mật khẩu quá dài!";
    public static final String TAX_CODE_REGISTER = "Mã số thuế không hợp lệ!";
    // section
    public static final String NAME_SECTION_NOT_BLANK_MESSAGE = "Tên khoa không được bỏ trống .";
    public static final String NAME_SECTION_NOT_LOGGER_MESSAGE = "Tên khoa không được dài quá 100 kí tự.";
    public static final String STATUS_SECTION_NOT_NULL_MESSAGE = "Trạng thái là bắt buộc .";
    public static final String CREATED_BY_SECTION_NOT_NULL_MESSAGE = "Người tạo là bắt buộc là bắt buộc.";
    public static final String UNIVERSITY_SECTION_NOT_NULL_MESSAGE = "Trường đại học là bắt buộc.";
    public static final String SECTION_NOT_FOUND_MESSAGE = "Không tìm thấy khoa.";
    public static final String SECTION_EXISTED_MESSAGE = "Tên khoa đã tồn tại .";
    public static final String SECTION_HAVE_MAJOR_MESSAGE = "Khoa có dữ liệu chuyên ngành không thể xóa.";
    public static final String SECTION_HAVE_ACTIVE_MAJOR_MESSAGE = "Vẫn còn chuyên ngành hoạt động không thể thay đổi trạng thái ";

    // major
    public static final String NAME_MAJOR_NOT_BLANK_MESSAGE = "Tên ngành không được bỏ trống .";
    public static final String NAME_MAJOR_NOT_LOGGER_MESSAGE = "Tên ngành không được dài quá 100 kí tự .";
    public static final String CODE_MAJOR_NOT_BLANK_MESSAGE = "Mã ngành không được bỏ trống .";
    public static final String CODE_MAJOR_NOT_LOGGER_MESSAGE = "Mã ngành không được dài quá 20 kí tự .";
    public static final String NUMBER_MIN_STUDENT_MAJOR_MESSAGE = "Số sinh viên ít nhất là 50 .";
    public static final String NUMBER_MAX_STUDENT_MAJOR_MESSAGE = "Tối đa chỉ được 1.000 sinh viên .";
    public static final String ID_MAJOR_NOT_NULL_MESSAGE = " Không tìm thấy khoa .";
    public static final String SECTION_INACTIVE_MESSAGE = "Khoa này hiện đang tạm ngưng";

    // Employee
    public static final String NAME_NOT_BLANK_MESSAGE = "Tên không được để trống";
    public static final String GENDER_NOT_BLANK_MESSAGE = "Giới tính không được để trống";
    public static final String DOB_NOT_BLANK_MESSAGE = "Ngày sinh không được để trống";
    public static final String EMAIL_NOT_BLANK_MESSAGE = "Email Tên không được để trống";
    public static final String EMAIL_INVALID_SPACE_MESSAGE = "Email không được bắt đầu bằng dấu cách";
    public static final String PHONE_NOT_BLANK_MESSAGE = "Số điện thoại không được để trống";
    public static final String CODE_EMPLOYEE_NOT_BLANK_MESSAGE = "Mã nhân viên không được để trống";
    public static final String TOO_LONG_NAME_MESSAGE = "Tên nhân viên không hợp lệ quá dài hoặc quá ngắn";
    public static final String INVALID_NAME_MESSAGE = "Tên nhân viên không được bắt đầu bằng dấu cách";
    public static final String TOO_LONG_ADDRESS_MESSAGE= "Địa chỉ vượt quá 255 ký tự";
    public static final String ADDRESS_INVALID_SPACE_MESSAGE = "Địa chỉ không được bắt đầu bằng dấu cách";



    // Administrative
    public static final String DISTRICT_NOT_FOUND_MESSAGE = "Không tìm thấy Quận/Huyện";
    public static final String PROVINCE_NOT_FOUND_MESSAGE = "Không tìm thấy Tỉnh/Thành phố";
    public static final String WARD_NOT_FOUND_MESSAGE = "Không tìm thấy Xã/Phường";
    public static final String PROVINCE_NOT_BLANK_MESSAGE = "Tỉnh/Thành phố không được để trống";
    public static final String DISTRICT_NOT_BLANK_MESSAGE = "Quận/Huyện không được để trống";
    public static final String WARD_NOT_BLANK_MESSAGE = "Xã/Phường không được để trống";

    // Location
    public static final String LOCATION_NOT_FOUND_MESSAGE = "Không tìm thấy location tron Database";

    // Exception database
    public static final String FILED_DB_NOT_UNIQUE_MESSAGE = "Trường trong database không duy nhất, hãy kiểm tra db";

    // Công việc đã đăng
    public static final String NO_EDIT_JOB = "Bạn không có quyền chỉnh sửa công viêc này";
    public static final String NO_INACTIVE_JOB = "Bạn không có quyền vô hiệu hóa công việc này";
    public static final String NO_EXIST_JOB = "Công việc không tồn tại";
    public static final String INACTIVE_JOB = "Vô hiệu hóa công việc thành công";
    public static final String ALREADY_INACTIVE = "Đã bị vô hiệu hóa trước đó";
    public static final String JOB_ALREADY_APPROVED_MESSAGE = "Tin tuyển dụng đã được chấp nhận.";
    public static final String JOB_ALREADY_REJECTED_MESSAGE = "Tin tuyển dụng đã bị từ chối.";
    public static final String INVALID_JOB_STATE_MESSAGE = "Trạng thái tin tuyển dụng không hợp lệ.";
    public static final String INVALID_TITLE = "Tiêu đề không được để trống";
    public static final String INVALID_TITLE_OUT = "Tiêu đề  không được vượt quá 250 ký tự";
    public static final String INVALID_SALARY = "Lương phải là số dương";
    public static final String INVALID_EXPIRED_DATE = "Ngày hết hạn không được để trống";
    public static final String INVALID_EXPIRED_DATE_FUTRURE = "Ngày hết hạn phải là ngày trong tương lai";
    public static final String ERROR_FROM_GREAT_TO_SALARY = "Lương nhập không hợp lệ";

    // Workshop
    public static final String NO_EXIST_WORKSHOP = "Công việc không tồn tại";
    public static final String WORKSHOP_ALREADY_APPROVED_MESSAGE = "Workshop này đã được chấp nhận.";
    public static final String WORKSHOP_ALREADY_REJECTED_MESSAGE = "Workshop này đã bị từ chối.";
    public static final String INVALID_WORKSHOP_STATE_MESSAGE = "Trạng thái workshop này không hợp lệ.";

    // Hợp tác
    public static final String HAVE_RELATION = "Bạn đã gửi yêu cầu hợp tác, vui lòng đợi chấp thuận";
    public static final String SEND_REQUEST_SUCCESS = "Đã gửi yêu cầu hợp tác thành công";
    public static final String APPROVED_RELATION = "Đã hợp tác";
    public static final String REJECTED_RELATION = "Yêu cầu hợp tác đã bị từ chối";
    public static final String CANCEL_RELATION = "Yêu cầu hợp tác đã bị hủy trước đó";
    public static final String CANCELED_SUCCESSFUL = "Hủy thành công";

    //Giáo vụ
    public static final String INSTRUCTIONAL_EXIST_MESSAGE ="Giáo vụ không tồn tại";
    public static final String INSTRUCTIONAL_CODE_EXISTED_MESSAGE ="Mã giáo vụ đã tồn tại";
    public static final String INSTRUCTIONAL_NAME_NOT_BANK_MESSAGE ="Tên giáo vụ không được để trống!";
    public static final String INSTRUCTIONAL_NAME_SIZE_MESSAGE ="Tên phải có độ dài từ 2 đến 100 ký tự!";
    public static final String INSTRUCTIONAL_GENDER_NOT_BLANK_MESSAGE ="Giới tính không được để trống!";
    public static final String INSTRUCTIONAL_GENDER_REGEX_MESSAGE ="Giới tính phải là Nam, Nữ hoặc Khác!";
    public static final String INSTRUCTIONAL_EMAIL_NOT_BLANK_MESSAGE ="Email không được để trống!";
    public static final String INSTRUCTIONAL_EMAIL_REGEX_MESSAGE ="Địa chỉ email không hợp lệ!";
    public static final String INSTRUCTIONAL_ADDRESS_NOT_BLANK_MESSAGE ="Địa chỉ không được để trống!";
    public static final String INSTRUCTIONAL_PHONE_NOT_BLANK_MESSAGE = "Số điện thoại không được để trống!";

    // Thông báo
    public static final String NOTIFICATION_NOT_FOUND_MESSAGE = "Thông báo không tồn tại";
    public static final String NOTIFICATION_ALREADY_READ_MESSAGE = "Thông báo đã được đánh dấu đã đọc.";

    // validate
    public static final String TEXT_REGEX="^(\\b\\w+\\b(\\s+|$)){1,200}$\n";
    public static final String NAME_REGEX= "^\\S.*$";
    public static final String EMAIL_REGEX= "^(?!\\s)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PHONE_REGEX= "^(\\+84|0)[0-9]\\d{8}$";
    // Thông báo validate
    public static final String CONTENT_TOO_LONG_MESSAGE = "Nội dung quá 200 từ";

    //Yêu cầu hợp tác
    public static final String REQUEST_COOPERATION = "Yêu cầu hợp tác";
    public static final String UNIVERSITY_JOB_EXITS = "Trường học đã đăng ký tin tuyển dụng.";
    public static final String UNIVERSITY_JOB_NOT_FOUND = "Yêu cầu không tồn tại.";
    public static final String NO_PENDING_UNIVERSITY_JOB = "Trạng thái ứng tuyển không hợp lệ ";
    public static final String APPROVED_UNIVERSITY_JOB = "Yêu cầu ứng tuyển của bạn đã được chấp thuận";
    public static final String REJECTED_UNIVERSITY_JOB = "Yêu cầu ứng tuyển của bạn đã bị từ chối";


}
