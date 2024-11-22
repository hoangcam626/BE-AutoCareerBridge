package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.AccountRespone.*;
import com.backend.autocarrerbridge.emailconfig.*;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;
import com.backend.autocarrerbridge.repository.*;

import com.backend.autocarrerbridge.service.UserAccountService;

import com.backend.autocarrerbridge.util.enums.State;
import com.backend.autocarrerbridge.util.password.PasswordGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.backend.autocarrerbridge.util.Constant.NOTIFICATION_NEW_PASSWORD;
import static com.backend.autocarrerbridge.util.Constant.NOTIFICATION_WAIT;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountServiceImpl implements UserAccountService {
     UserAccountRepository userAccountRepository;
     ModelMapper modelMapper;
     PasswordEncoder passwordEncoder;
     SendEmail sendEmail;
     RedisTemplate<String,String> redisTemplate;
     String codeExist = "Exist";
     Integer codeTime = 60;

    @Override
    public DisplayUserAccountDTO authenticateUser(UserAccountResponseDTO useraccountDTO) {

        if (useraccountDTO.getUsername() == null || useraccountDTO.getPassword() == null) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }
        UserAccount user = userAccountRepository.findByUsername(useraccountDTO.getUsername());
        if (user == null) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }

        if (passwordEncoder.matches(useraccountDTO.getPassword(), user.getPassword())) {
            if(user.getState().equals(State.PENDING)){
                throw new AppException(ErrorCode.ERROR_USER_PENDING);
            }
            UserAccountResponseDTO userAccountResponseDTO = new UserAccountResponseDTO();
            userAccountResponseDTO.setStatus(user.getStatus());
            userAccountResponseDTO.setId(user.getId());
            userAccountResponseDTO.setUsername(user.getUsername());
            userAccountResponseDTO.setPassword(user.getPassword());
            userAccountResponseDTO.setRole(modelMapper.map(user.getRole(), RoleDTO.class));

            return  modelMapper.map(userAccountResponseDTO,DisplayUserAccountDTO.class);
        } else {
            throw new AppException(ErrorCode.ERROR_PASSWORD_INCORRECT);
        }
    }

    @Override
    public void saveRefreshTokenForUser(Integer id, String refresh_token) {
        UserAccount userAccounts = userAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userAccounts.setRefreshToken(refresh_token);
        userAccountRepository.save(userAccounts);
    }

    @Override
    public UserAccount getUserByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Override
    public UserAccount registerUser(UserAccount userAccount) {

        UserAccount newAccount = UserAccount.builder()
                .username(userAccount.getUsername())
                .password(passwordEncoder.encode(userAccount.getPassword()))
                .role(userAccount.getRole())
                .state(userAccount.getState())
                .build();
        return userAccountRepository.save(newAccount);
    }

    @Override
    public UserAccount approvedAccount(UserAccount userAccount){
        if(userAccount.getState() == State.PENDING){
            userAccount.setState(State.APPROVED);
        }
        return userAccountRepository.save(userAccount);
    }
 //   @PreAuthorize("hasAuthority('SCOPE_Admin')")
    @Override
    public DisplayUserAccountDTO updatePassword(ChangePassWordDTO userAccountResponeDTO) {
        UserAccount userAccount = userAccountRepository.findByUsername(userAccountResponeDTO.getUsername());
        if (userAccountResponeDTO.getPassword() == null || userAccountResponeDTO.getPassword().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }
        if(passwordEncoder.matches(userAccountResponeDTO.getNewPassword(), userAccount.getPassword())){
            throw new AppException(ErrorCode.ERROR_PASSWORD_SAME);
        }
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        if(!userAccountResponeDTO.getNewPassword().equals(userAccountResponeDTO.getReNewPassword())){
            throw new AppException(ErrorCode.ERROR_PASSWORD_NOT_MATCH);
        }
        if(!passwordEncoder.matches(userAccountResponeDTO.getPassword(), userAccount.getPassword())){
           throw new AppException(ErrorCode.ERROR_PASSWORD_INCORRECT);
        }

        userAccount.setPassword(passwordEncoder.encode(userAccountResponeDTO.getNewPassword()));
        return modelMapper.map(userAccountRepository.save(userAccount),DisplayUserAccountDTO.class);
    }
    @Override
    public EmailCode generateVerificationCode(String email) {
        String code = generateCode(email);
        if(code.equals(codeExist)){
            return EmailCode.builder().email(email).code("Please wait").build();
        }
        return EmailCode.builder().email(email).code(code).build();
    }

    @Override
    public EmailCode generatePasswordResetCode(String email) {
        String code = generateCodeForgot(email);
        if(code.equals(codeExist)){
            return EmailCode.builder().email(email).code("Please wait").build();
        }
        return EmailCode.builder().email(email).code(code).build();
    }

    @Override
    public String handleForgotPassword(ForgotPassWordDTO forgotPassWordDTO) {
        if(forgotPassWordDTO.getEmail() == null || forgotPassWordDTO.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }
        if(userAccountRepository.findByUsername(forgotPassWordDTO.getEmail()) == null){
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }
        if(!forgotPassWordDTO.getForgotCode().equals(redisTemplate.opsForValue().get("fg/" + forgotPassWordDTO.getEmail()))){
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }

        PasswordGenerator pw = new PasswordGenerator(8, 12);
        String newPassword = pw.generatePassword();
        UserAccount userAccount = userAccountRepository.findByUsername(forgotPassWordDTO.getEmail());
        userAccount.setPassword(passwordEncoder.encode(newPassword));
        userAccountRepository.save(userAccount);
        if(generateNewPassword(forgotPassWordDTO.getEmail(),newPassword).equals(codeExist)){
            return NOTIFICATION_WAIT;
        }
        return NOTIFICATION_NEW_PASSWORD;
    }
    public String generateNewPassword(String emailSend, String generatedCode) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey("/np"+ emailSend))){
            return codeExist;
        }
        Email email = new Email(emailSend,"Xác nhận mật khẩu mới","");
        sendEmail.sendNewPassword(email, generatedCode);
        redisTemplate.opsForValue().set("/np" + emailSend,generatedCode,codeTime, TimeUnit.SECONDS);
        return generatedCode;
    }
    public String generateCode(String emailSend) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey(emailSend))){
            return codeExist;
        }
        Email email = new Email(emailSend,"Xác nhận đăng ký tài khoản","");
        String generatedCode = RandomCodeGenerator.generateRegistrationCode();
        sendEmail.sendCode(email, generatedCode);
        redisTemplate.opsForValue().set(emailSend,generatedCode,codeTime, TimeUnit.SECONDS);
        return generatedCode;
    }
    public String generateCodeForgot(String emailSend) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey(emailSend))){
            return codeExist;
        }
        Email email = new Email(emailSend,"Mã cấp mật khẩu mới!","");
        String generatedCode = RandomCodeGenerator.generateRegistrationCode();
        sendEmail.sendForgot(email, generatedCode);
        redisTemplate.opsForValue().set("fg/"+  emailSend,generatedCode,codeTime, TimeUnit.SECONDS);
        return generatedCode;
    }
}
