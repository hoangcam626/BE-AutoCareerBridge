package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_EMAIL_EXIST;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_NOT_FOUND_SUB_ADMIN;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_SUB_ADMIN_CODE_EXIST;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_VALID_EMAIL;
import static com.backend.autocarrerbridge.exception.ErrorCode.NO_CHANGE_DETECTED;
import static com.backend.autocarrerbridge.util.Constant.ACCOUNT;
import static com.backend.autocarrerbridge.util.Constant.SUB;

import java.text.ParseException;
import java.util.List;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminCreateRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminDeleteRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminSelfRequest;
import com.backend.autocarrerbridge.dto.request.subadmin.SubAdminUpdateRequest;
import com.backend.autocarrerbridge.dto.response.subAdmin.SubAdminCreateResponse;
import com.backend.autocarrerbridge.dto.response.subAdmin.SubAdminDeleteResponse;
import com.backend.autocarrerbridge.dto.response.subAdmin.SubAdminSelfResponse;
import com.backend.autocarrerbridge.dto.response.subAdmin.SubAdminUpdateResponse;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.SendEmail;
import com.backend.autocarrerbridge.entity.SubAdmin;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.repository.SubAdminRepository;
import com.backend.autocarrerbridge.service.ImageService;
import com.backend.autocarrerbridge.service.RoleService;
import com.backend.autocarrerbridge.service.SubAdminService;
import com.backend.autocarrerbridge.service.TokenService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.Validation;
import com.backend.autocarrerbridge.util.enums.PredefinedRole;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.enums.Status;
import com.backend.autocarrerbridge.util.password.PasswordGenerator;

import lombok.RequiredArgsConstructor;

/**
 * SubAdminServiceImpl là lớp triển khai các chức năng liên quan đến việc quản lý sub-admin trong hệ thống.
 * - Lấy danh sách sub-admin (phân trang hoặc toàn bộ).
 * - Tạo mới, cập nhật, xóa, lấy chi tiết sub-admin.
 * Sử dụng @Transactional đảm bảo toàn vẹn dữ liệu.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class SubAdminServiceImpl implements SubAdminService {

    private final SubAdminRepository subAdminRepository;
    private final UserAccountService userAccountService;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final ModelMapper modelMapper;
    private final SendEmail sendEmail;

    /**
     * Lấy danh sách sub-admin theo phân trang.
     *
     * @param page     - Số trang cần lấy.
     * @param pageSize - Số lượng phần tử trong mỗi trang.
     * @return Danh sách sub-admin dưới dạng đối tượng phân trang.
     */
    @Override
    public Page<SubAdminSelfResponse> pageSubAdmins(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<SubAdmin> subAdmins = subAdminRepository.findAllPageable(pageable);
        return subAdmins.map(subAdmin -> modelMapper.map(subAdmin, SubAdminSelfResponse.class));
    }

    /**
     * Lấy danh sách tất cả sub-admin đang hoạt động.
     *
     * @return Danh sách sub-admin.
     */
    @Override
    public List<SubAdminSelfResponse> listSubAdmins() {
        List<SubAdmin> subAdmins = subAdminRepository.findAllByStatus();
        return subAdmins.stream()
                .map(subAdmin -> modelMapper.map(subAdmin, SubAdminSelfResponse.class))
                .toList();
    }

    /**
     * Tạo một sub-admin mới
     *
     * @param req - Thông tin đầu vào để tạo sub-admin
     * @return Thông tin đối tượng chứa kết quả
     * @throws ParseException - Có lỗi trong quá trình lấy thông tin từ token
     */
    @Override
    public SubAdminCreateResponse create(SubAdminCreateRequest req) throws ParseException {

        validateCreate(req); // Gọi hàm kiểm tra dữ liệu đầu vào
        var subAdmin = modelMapper.map(req, SubAdmin.class); // Map thông tin dữ liệu đầu vào
        var imgId = imageService.uploadFile(req.getSubAdminImage()); // Gọi hàm lưu ảnh
        subAdmin.setSubAdminImageId(imgId);

        // Gọi hàm sinh password tự động và khởi tạo tài khoản người dùng
        PasswordGenerator pw = new PasswordGenerator(8, 12);
        String password = pw.generatePassword();
        UserAccount newAccount = new UserAccount();
        newAccount.setUsername(req.getEmail());
        newAccount.setPassword(password);
        newAccount.setState(State.APPROVED);
        newAccount.setRole(roleService.findById(PredefinedRole.SUB_ADMIN.getValue()));
        subAdmin.setUserAccount(userAccountService.registerUser(newAccount));

        // Lấy tên người dùng đang đăng nhập để lưu người tạo cho đối tượng
        var nameAccountLogin = tokenService.getClaim(tokenService.getJWT(), SUB);
        subAdmin.setCreatedBy(nameAccountLogin);

        subAdmin = subAdminRepository.save(subAdmin); // Lưu tài khoản

        // Gửi mail thông báo tài khoản đăng nhập
        EmailDTO emailDTO = new EmailDTO(subAdmin.getEmail(), ACCOUNT, "");
        sendEmail.sendAccount(emailDTO, password);

        return SubAdminCreateResponse.of(subAdmin.getId());
    }

    /**
     * Cập nhật thông tin sub-admin.
     *
     * @param req - Thông tin đầu vào để cập nhật sub-admin.
     * @return Thông tin phản hồi cập nhật thành công.
     * @throws ParseException - Có lỗi trong quá trình lấy thông tin từ token.
     */
    @Override
    public SubAdminUpdateResponse update(SubAdminUpdateRequest req) throws ParseException {
        // Tìm đối tượng cập nhật bắng id
        var subAdmin = getSubAdmin(req.getId());

        // Các biến kiểm tra các trường đầu vào có thay đổi so với database hay không
        boolean isSameAddress = subAdmin.getAddress().equals(req.getAddress());
        boolean isSamePhone = subAdmin.getPhone().equals(req.getPhone());
        boolean isNullImage =
                req.getSubAdminImage() == null || req.getSubAdminImage().isEmpty();

        // Bắn thông báo không có gì thay đổi nếu tất cả các biến là true
        if (isSameAddress && isSamePhone && isNullImage) {
            throw new AppException(NO_CHANGE_DETECTED);
        }

        // Lưu thông tin trường nếu thay đổi
        if (!isSameAddress) {
            subAdmin.setAddress(req.getAddress());
        }
        if (!isSamePhone) {
            subAdmin.setPhone(req.getPhone());
        }
        if (!isNullImage) {
            var imgId = imageService.uploadFile(req.getSubAdminImage());
            subAdmin.setSubAdminImageId(imgId);
        }

        // Lưu người thay đổi
        var nameAccountLogin = tokenService.getClaim(tokenService.getJWT(), SUB);
        subAdmin.setUpdatedBy(nameAccountLogin);

        // Lưu vào database
        subAdminRepository.save(subAdmin);
        return SubAdminUpdateResponse.of(Boolean.TRUE);
    }

    /**
     * Lấy thông tin chi tiết của một sub-admin.
     *
     * @param req - Thông tin đầu vào để tìm kiếm.
     * @return Thông tin chi tiết của sub-admin.
     */
    @Override
    public SubAdminSelfResponse self(SubAdminSelfRequest req) {
        // Tìm đối tượng lấy ra bằng id
        var subAdmin = getSubAdmin(req.getId());
        return modelMapper.map(subAdmin, SubAdminSelfResponse.class);
    }

    /**
     * Xóa mềm (ẩn) một sub-admin.
     *
     * @param req - Thông tin đầu vào để xóa.
     * @return Phản hồi sau khi xóa.
     */
    @Override
    public SubAdminDeleteResponse delete(SubAdminDeleteRequest req) {
        var subAdmin = getSubAdmin(req.getId());
        subAdmin.setStatus(Status.INACTIVE);
        subAdminRepository.save(subAdmin);
        return SubAdminDeleteResponse.of(Boolean.TRUE);
    }

    /**
     * Tìm kiếm sub-admin theo id.
     *
     * @param id - Id của sub-admin cần tìm.
     * @return Đối tượng sub-admin.
     */
    public SubAdmin getSubAdmin(Integer id) {
        return subAdminRepository.findById(id).orElseThrow(() -> new AppException(ERROR_NOT_FOUND_SUB_ADMIN));
    }

    /**
     * Kiểm tra thông tin đầu vào khi tạo sub-admin.
     *
     * @param req - Thông tin cần kiểm tra.
     */
    public void validateCreate(SubAdminCreateRequest req) {
        if (!Validation.isValidEmail(req.getEmail())) {
            throw new AppException(ERROR_VALID_EMAIL);
        }
        if (subAdminRepository.existsBySubAdminCode(req.getSubAdminCode())) {
            throw new AppException(ERROR_SUB_ADMIN_CODE_EXIST);
        }
        if (subAdminRepository.existsByEmail(req.getEmail())) {
            throw new AppException(ERROR_EMAIL_EXIST);
        }
    }
}
