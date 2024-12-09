package com.backend.autocarrerbridge.service.impl;

import static com.backend.autocarrerbridge.exception.ErrorCode.*;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_ACCOUNT_ALREADY_APPROVED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_ACCOUNT_ALREADY_REJECTED;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_ACCOUNT_IS_NULL;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_INVALID_ACCOUNT_STATE;
import static com.backend.autocarrerbridge.exception.ErrorCode.ERROR_USER_NOT_FOUND;
import static com.backend.autocarrerbridge.util.Constant.ACCEPT_NP;
import static com.backend.autocarrerbridge.util.Constant.ACCEPT_US;
import static com.backend.autocarrerbridge.util.Constant.NEW_CODE;
import static com.backend.autocarrerbridge.util.Constant.NOTIFICATION_NEW_PW;
import static com.backend.autocarrerbridge.util.Constant.NOTIFICATION_WAIT;
import static com.backend.autocarrerbridge.util.Constant.PREFIX_FG;
import static com.backend.autocarrerbridge.util.Constant.PREFIX_NP;

import java.util.concurrent.TimeUnit;

import com.backend.autocarrerbridge.dto.response.business.BusinessLoginResponse;

import com.backend.autocarrerbridge.dto.response.subadmin.SubAdminSelfResponse;
import com.backend.autocarrerbridge.dto.response.university.UniversityResponse;


import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.entity.SubAdmin;
import com.backend.autocarrerbridge.entity.University;
import com.backend.autocarrerbridge.service.IntermediaryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.autocarrerbridge.dto.request.account.ForgotPasswordRequest;
import com.backend.autocarrerbridge.dto.request.account.PasswordChangeRequest;
import com.backend.autocarrerbridge.dto.request.account.RoleRequest;
import com.backend.autocarrerbridge.dto.request.account.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.account.UserAccountLoginResponse;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.UserAccountRepository;

import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.util.email.EmailCode;
import com.backend.autocarrerbridge.util.email.EmailDTO;
import com.backend.autocarrerbridge.util.email.RandomCodeGenerator;
import com.backend.autocarrerbridge.util.email.SendEmail;
import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.password.PasswordGenerator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountServiceImpl implements UserAccountService {
    UserAccountRepository userAccountRepository;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;
    SendEmail sendEmail;
    RedisTemplate<String, String> redisTemplate;
    String codeExist = "Exist";
    Integer codeTime = 60;
    IntermediaryService intermediaryService;

    /**
     * Xác thực người dùng dựa trên tên đăng nhập và mật khẩu.
     *
     * @param userAccountRequest đối tượng chứa thông tin tên đăng nhập và mật khẩu.
     * @return phản hồi đăng nhập chứa thông tin tài khoản nếu xác thực thành công.
     * @throws AppException nếu không tìm thấy tài khoản, mật khẩu không đúng, hoặc tài khoản đang chờ duyệt.
     */
    @Override
    public UserAccountLoginResponse authenticateUser(UserAccountRequest userAccountRequest) {

        validateRequest(userAccountRequest);
        UserAccount user = userAccountRepository.findByUsername(userAccountRequest.getUsername());
        if (user == null) {
            throw new AppException(ERROR_USER_NOT_FOUND);
        }
        validatePassword(userAccountRequest.getPassword(), user.getPassword());
        validateUserState(user.getState());

        // Chuyển thông tin từ user -> response
        UserAccountLoginResponse userAccountLoginResponse = new UserAccountLoginResponse();
        modelMapper.map(user, userAccountLoginResponse);
        userAccountLoginResponse.setRole(modelMapper.map(user.getRole(), RoleRequest.class));

        switch (user.getRole().getName()) {
            case "BUSINESS":
                Business business = intermediaryService.findBusinessByEmail(userAccountRequest.getUsername());
                if (business != null) {
                    userAccountLoginResponse.setBusiness(modelMapper.map(business, BusinessLoginResponse.class));
                }
                break;
            case "UNIVERSITY":
                University university = intermediaryService.findUniversityByEmail(userAccountRequest.getUsername());
                if (university != null) {
                    userAccountLoginResponse.setUniversity(modelMapper.map(university, UniversityResponse.class));
                }
                break;
            case "SUB_ADMIN":
                SubAdmin subAdmin = intermediaryService.findSubAdminByEmail(userAccountRequest.getUsername());
                if (subAdmin != null) {
                    userAccountLoginResponse.setSubAdmin(modelMapper.map(subAdmin, SubAdminSelfResponse.class));
                }
                break;
            default:
                break;
        }

        return userAccountLoginResponse;
    }

    /**
     * Lưu refresh token cho tài khoản người dùng.
     *
     * @param id id của tài khoản người dùng.
     * @param refreshToken token cần lưu.
     * @throws RuntimeException nếu không tìm thấy tài khoản.
     */
    @Override
    public void saveRefreshTokenForUser(Integer id, String refreshToken) {
        UserAccount userAccounts = userAccountRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(ERROR_USER_NOT_FOUND.getMessage()));
        userAccounts.setRefreshToken(refreshToken);
        userAccountRepository.save(userAccounts);
    }

    @Override
    public UserAccount getUserByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    /**
     * Đăng ký tài khoản người dùng mới.
     *
     * @param userAccount đối tượng tài khoản người dùng cần đăng ký.
     * @return tài khoản đã được lưu vào cơ sở dữ liệu.
     */
    @Override
    public UserAccount registerUser(UserAccount userAccount) {
        if(Boolean.TRUE.equals(userAccountRepository.existsByUsername(userAccount.getUsername()))){
            throw new AppException(ERROR_EMAIL_EXIST);
        }
        UserAccount newAccount = UserAccount.builder()
                .username(userAccount.getUsername())
                .password(passwordEncoder.encode(userAccount.getPassword()))
                .role(userAccount.getRole())
                .state(userAccount.getState())
                .build();
        return userAccountRepository.save(newAccount);
    }

    @Override
    public void approvedAccount(UserAccount req) {
        validateAccountForStateChange(req, State.APPROVED);
        req.setState(State.APPROVED);
        userAccountRepository.save(req);
    }

    @Override
    public void rejectedAccount(UserAccount req) {
        validateAccountForStateChange(req, State.REJECTED);
        req.setState(State.REJECTED);
        userAccountRepository.save(req);
    }
    //   @PreAuthorize("hasAuthority('SCOPE_Admin')")
    //   @PreAuthorize("hasAuthority('SCOPE_Admin')")

    /**
     * Cập nhật mật khẩu cho tài khoản người dùng.
     *
     * @param userAccountResponseDTO đối tượng chứa thông tin cập nhật mật khẩu.
     * @return phản hồi đăng nhập với thông tin mới.
     * @throws AppException nếu mật khẩu không hợp lệ hoặc không khớp.
     */
    @Override
    public UserAccountLoginResponse updatePassword(PasswordChangeRequest userAccountResponseDTO) {
        UserAccount userAccount = userAccountRepository.findByUsername(userAccountResponseDTO.getUsername());
        if (userAccountResponseDTO.getPassword() == null
                || userAccountResponseDTO.getPassword().isEmpty()) {
            throw new AppException(ERROR_USER_NOT_FOUND);
        }
        if (passwordEncoder.matches(userAccountResponseDTO.getNewPassword(), userAccount.getPassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_SAME);
        }
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        if (!userAccountResponseDTO.getNewPassword().equals(userAccountResponseDTO.getReNewPassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }
        if (!passwordEncoder.matches(userAccountResponseDTO.getPassword(), userAccount.getPassword())) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_INCORRECT);
        }

        userAccount.setPassword(passwordEncoder.encode(userAccountResponseDTO.getNewPassword()));
        return modelMapper.map(userAccountRepository.save(userAccount), UserAccountLoginResponse.class);
    }

    /**
     * Tạo mã xác minh cho email.
     *
     * @param email email cần tạo mã.
     * @return mã xác minh gửi đến email.
     * @throws AppException nếu tài khoản đã được duyệt.
     */
    @Override
    public EmailCode generateVerificationCode(String email) {
        UserAccount userAccount = userAccountRepository.findByUsername(email);
        if (userAccount != null && userAccount.getState().equals(State.APPROVED)) {
            throw new AppException(ErrorCode.ERROR_USER_APPROVED);
        }
        String code = generateCode(email);
        if (code.equals(codeExist)) {
            return EmailCode.builder().email(email).verificationCode(NOTIFICATION_WAIT).build();
        }
        return EmailCode.builder().email(email).verificationCode(code).build();
    }

    // Gen ra mã reset mật khẩu
    @Override
    public EmailCode generatePasswordResetCode(String email) {
        if(userAccountRepository.findByUsername(email) == null){
            throw new AppException(ERROR_USER_NOT_FOUND);
        }
        // Tạo mã reset mật khẩu cho email
        String code = generateCodeForgot(email);

        // Nếu mã đã tồn tại, trả về thông báo yêu cầu đợi
        if (code.equals(codeExist)) {
            throw new AppException(ERROR_FORGOT_EMAIL);
        }

        // Trả về mã reset mật khẩu mới
        return EmailCode.builder().email(email).verificationCode(code).build();
    }

    // Thực hiện thay đổi mật khẩu khi quên mật khẩu
    @Override
    public String handleForgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        // Kiểm tra email có hợp lệ không
        if (forgotPasswordRequest.getEmail() == null
                || forgotPasswordRequest.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }

        // Kiểm tra xem email có tồn tại trong hệ thống không
        if (userAccountRepository.findByUsername(forgotPasswordRequest.getEmail()) == null) {
            throw new AppException(ERROR_USER_NOT_FOUND);
        }

        // Kiểm tra mã xác nhận có khớp với mã lưu trong Redis không
        if (!forgotPasswordRequest
                .getForgotCode()
                .equals(redisTemplate.opsForValue().get(PREFIX_FG + forgotPasswordRequest.getEmail()))) {
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }

        // Tạo mật khẩu mới với độ dài từ 8 đến 12 ký tự
        PasswordGenerator pw = new PasswordGenerator(8, 12);
        String newPassword = pw.generatePassword();

        // Mã hóa mật khẩu mới và lưu vào cơ sở dữ liệu
        UserAccount userAccount = userAccountRepository.findByUsername(forgotPasswordRequest.getEmail());
        userAccount.setPassword(passwordEncoder.encode(newPassword));
        userAccountRepository.save(userAccount);

        // Gửi mật khẩu mới qua email và kiểm tra trạng thái gửi
        if (generateNewPassword(forgotPasswordRequest.getEmail(), newPassword).equals(codeExist)) {
            return NOTIFICATION_WAIT;
        }

        return NOTIFICATION_NEW_PW;
    }

    // Tạo mật khẩu mới và gửi qua email
    public String generateNewPassword(String emailSend, String generatedCode) {
        // Kiểm tra xem mã mới đã tồn tại trong Redis chưa
        if (Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX_NP + emailSend))) {
            return codeExist;
        }

        // Gửi email thông báo mật khẩu mới
        EmailDTO emailDTO = new EmailDTO(emailSend, ACCEPT_NP, "");
        sendEmail.sendNewPassword(emailDTO, generatedCode);

        // Lưu mã mới vào Redis với thời gian hết hạn
        redisTemplate.opsForValue().set(PREFIX_NP + emailSend, generatedCode, codeTime, TimeUnit.SECONDS);

        return generatedCode;
    }

    // Tạo mã xác nhận tài khoản mới
    public String generateCode(String emailSend) {
        // Kiểm tra xem mã đã tồn tại trong Redis chưa
        if (Boolean.TRUE.equals(redisTemplate.hasKey(emailSend))) {
            return codeExist;
        }

        // Tạo mã ngẫu nhiên và gửi qua email
        EmailDTO emailDTO = new EmailDTO(emailSend, ACCEPT_US, "");
        String generatedCode = RandomCodeGenerator.generateRegistrationCode();
        sendEmail.sendCode(emailDTO, generatedCode);

        // Lưu mã vào Redis với thời gian hết hạn
        redisTemplate.opsForValue().set(emailSend, generatedCode, codeTime, TimeUnit.SECONDS);

        return generatedCode;
    }

    // Tạo mã quên mật khẩu và gửi qua email
    public String generateCodeForgot(String emailSend) {
        // Kiểm tra xem mã quên mật khẩu đã tồn tại chưa
        if (Boolean.TRUE.equals(redisTemplate.hasKey(emailSend))) {
            return codeExist;
        }

        // Tạo mã ngẫu nhiên và gửi qua email
        EmailDTO emailDTO = new EmailDTO(emailSend, NEW_CODE, "");
        String generatedCode = RandomCodeGenerator.generateRegistrationCode();
        sendEmail.sendForgot(emailDTO, generatedCode);

        // Lưu mã quên mật khẩu vào Redis với thời gian hết hạn
        redisTemplate.opsForValue().set(PREFIX_FG + emailSend, generatedCode, codeTime, TimeUnit.SECONDS);

        return generatedCode;
    }

    // Xác thực trạng thái tài khoản khi thay đổi
    private void validateAccountForStateChange(UserAccount req, State targetState) {
        // Kiểm tra nếu tài khoản không tồn tại
        if (req == null) {
            throw new AppException(ERROR_ACCOUNT_IS_NULL);
        }

        // Kiểm tra nếu trạng thái hiện tại giống với trạng thái mục tiêu
        if (req.getState() == targetState) {
            throw new AppException(
                    targetState == State.APPROVED ? ERROR_ACCOUNT_ALREADY_APPROVED : ERROR_ACCOUNT_ALREADY_REJECTED);
        }

        // Chỉ cho phép thay đổi trạng thái từ PENDING
        if (req.getState() != State.PENDING) {
            throw new AppException(ERROR_INVALID_ACCOUNT_STATE);
        }
    }

    private void validateRequest(UserAccountRequest userAccountRequest) {
        if (userAccountRequest.getUsername() == null
                || userAccountRequest.getPassword() == null
                || userAccountRequest.getUsername().isEmpty()
                || userAccountRequest.getPassword().isEmpty()) {
            throw new AppException(ERROR_USER_NOT_FOUND);
        }
    }

    private void validatePassword(String inputPassword, String storedPassword) {
        if (!passwordEncoder.matches(inputPassword, storedPassword)) {
            throw new AppException(ErrorCode.ERROR_PASSWORD_INCORRECT);
        }
    }

    private void validateUserState(State state) {
        if (state.equals(State.PENDING)) {
            throw new AppException(ErrorCode.ERROR_USER_PENDING);
        }
    }
}
