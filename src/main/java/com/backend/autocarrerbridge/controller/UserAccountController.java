package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.AccountRespone.DisplayUserAccountDTO;
import com.backend.autocarrerbridge.dto.AccountRespone.UserAccountResponeDTO;
import com.backend.autocarrerbridge.dto.AccountRespone.UserBusinessDTO;
import com.backend.autocarrerbridge.dto.AccountRespone.UserUniversityDTO;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.service.UserAuthentication;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountController {
     UserAccountService userAccountService;
     AuthenticationService authenticationService;
     UserAuthentication userAuthentication;
    @PostMapping("/register")
    private ApiResponse<?> registerBussiness(@ModelAttribute UserBusinessDTO userBusinessDTO){
        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .data(userAccountService
                .registerBusiness(userBusinessDTO))
                .build();
    }
    @PostMapping("/register/uni")
    private ApiResponse<?> registerUniversity(@RequestBody UserUniversityDTO userUniversityDTO){

        return ApiResponse.builder()
                .code(200)
                .message("Success")
                .data(userAccountService.registerUniversity(userUniversityDTO))
                .build();
    }
    @PostMapping("/login")
    public ApiResponse<?> loginJWT(@RequestBody UserAccountResponeDTO accountDTO)  {
        DisplayUserAccountDTO useraccountDTO = userAccountService.login(accountDTO);
        AuthenticationResponse authenticationResponse =  userAuthentication.authenticate(accountDTO.getUsername());
        useraccountDTO.setAccessToken(authenticationResponse.getAccessToken());

        return ApiResponse.builder().message("Login success").code(200).data(useraccountDTO).build();
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestHeader("Authorization") String authorizationHeader) throws ParseException {
        String token = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
        }
        authenticationService.logout(token);
        return ApiResponse.builder().message("Logout success").data("Logout Success").code(200).build();
    }
    @PostMapping("/refresh")
    public ApiResponse<?> refresh(@RequestBody String token) throws ParseException {
        String newToken = authenticationService.getNewToken(token);
        return ApiResponse.builder().message("Refresh Token Success").code(200).data(newToken).build();
    }
}
