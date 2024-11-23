package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.dto.request.account.ForgotPasswordRequest;
import com.backend.autocarrerbridge.dto.request.account.PasswordChangeRequest;
import com.backend.autocarrerbridge.dto.request.account.RoleRequest;
import com.backend.autocarrerbridge.dto.request.account.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.account.UserAccountLoginResponse;

import com.backend.autocarrerbridge.emailconfig.EmailCode;
import com.backend.autocarrerbridge.emailconfig.EmailDTO;
import com.backend.autocarrerbridge.emailconfig.RandomCodeGenerator;
import com.backend.autocarrerbridge.emailconfig.SendEmail;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.exception.AppException;
import com.backend.autocarrerbridge.exception.ErrorCode;

import com.backend.autocarrerbridge.repository.UserAccountRepository;
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


import static com.backend.autocarrerbridge.util.Constant.ACCEPT_NP;
import static com.backend.autocarrerbridge.util.Constant.ACCEPT_US;
import static com.backend.autocarrerbridge.util.Constant.NEW_CODE;
import static com.backend.autocarrerbridge.util.Constant.NOTIFICATION_NEW_PW;
import static com.backend.autocarrerbridge.util.Constant.NOTIFICATION_WAIT;
import static com.backend.autocarrerbridge.util.Constant.PREFIX_FG;
import static com.backend.autocarrerbridge.util.Constant.PREFIX_NP;

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
    public UserAccountLoginResponse authenticateUser(UserAccountRequest useraccountDTO) {

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
            UserAccountRequest userAccountRequest = new UserAccountRequest();
            userAccountRequest.setStatus(user.getStatus());
            userAccountRequest.setId(user.getId());
            userAccountRequest.setUsername(user.getUsername());
            userAccountRequest.setPassword(user.getPassword());
            userAccountRequest.setRole(modelMapper.map(user.getRole(), RoleRequest.class));

            return  modelMapper.map(userAccountRequest, UserAccountLoginResponse.class);
        } else {
            throw new AppException(ErrorCode.ERROR_PASSWORD_INCORRECT);
        }
    }

    @Override
    public void saveRefreshTokenForUser(Integer id, String refreshToken) {
        UserAccount userAccounts = userAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ErrorCode.ERROR_USER_NOT_FOUND.getMessage()));
        userAccounts.setRefreshToken(refreshToken);
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
    public UserAccountLoginResponse updatePassword(PasswordChangeRequest userAccountResponeDTO) {
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
        return modelMapper.map(userAccountRepository.save(userAccount), UserAccountLoginResponse.class);
    }
    @Override
    public EmailCode generateVerificationCode(String email) {
        String code = generateCode(email);
        if(userAccountRepository.findByUsername(email).getState().equals(State.APPROVED)){
            throw new AppException(ErrorCode.ERROR_USER_APPROVED);
        }
        if(code.equals(codeExist)){
            return EmailCode.builder().email(email).code(NOTIFICATION_WAIT).build();
        }
        return EmailCode.builder().email(email).code(code).build();
    }

    @Override
    public EmailCode generatePasswordResetCode(String email) {
        String code = generateCodeForgot(email);
        if(code.equals(codeExist)){
            return EmailCode.builder().email(email).code(NOTIFICATION_WAIT).build();
        }
        return EmailCode.builder().email(email).code(code).build();
    }

    @Override
    public String handleForgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        if(forgotPasswordRequest.getEmail() == null || forgotPasswordRequest.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }
        if(userAccountRepository.findByUsername(forgotPasswordRequest.getEmail()) == null){
            throw new AppException(ErrorCode.ERROR_USER_NOT_FOUND);
        }
        if(!forgotPasswordRequest.getForgotCode().equals(redisTemplate.opsForValue().get(PREFIX_FG + forgotPasswordRequest.getEmail()))){
            throw new AppException(ErrorCode.ERROR_VERIFY_CODE);
        }

        PasswordGenerator pw = new PasswordGenerator(8, 12);
        String newPassword = pw.generatePassword();
        UserAccount userAccount = userAccountRepository.findByUsername(forgotPasswordRequest.getEmail());
        userAccount.setPassword(passwordEncoder.encode(newPassword));
        userAccountRepository.save(userAccount);
        if(generateNewPassword(forgotPasswordRequest.getEmail(),newPassword).equals(codeExist)){
            return NOTIFICATION_WAIT;
        }
        return NOTIFICATION_NEW_PW;
    }
    public String generateNewPassword(String emailSend, String generatedCode) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX_NP+ emailSend))){
            return codeExist;
        }
        EmailDTO emailDTO = new EmailDTO(emailSend,ACCEPT_NP,"");
        sendEmail.sendNewPassword(emailDTO, generatedCode);
        redisTemplate.opsForValue().set(PREFIX_NP + emailSend,generatedCode,codeTime, TimeUnit.SECONDS);
        return generatedCode;
    }
    public String generateCode(String emailSend) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey(emailSend))){
            return codeExist;
        }
        EmailDTO emailDTO = new EmailDTO(emailSend,ACCEPT_US,"");
        String generatedCode = RandomCodeGenerator.generateRegistrationCode();
        sendEmail.sendCode(emailDTO, generatedCode);
        redisTemplate.opsForValue().set(emailSend,generatedCode,codeTime, TimeUnit.SECONDS);
        return generatedCode;
    }
    public String generateCodeForgot(String emailSend) {
        if(Boolean.TRUE.equals(redisTemplate.hasKey(emailSend))){
            return codeExist;
        }
        EmailDTO emailDTO = new EmailDTO(emailSend,NEW_CODE,"");
        String generatedCode = RandomCodeGenerator.generateRegistrationCode();
        sendEmail.sendForgot(emailDTO, generatedCode);
        redisTemplate.opsForValue().set(PREFIX_FG+  emailSend,generatedCode,codeTime, TimeUnit.SECONDS);
        return generatedCode;
    }
}
