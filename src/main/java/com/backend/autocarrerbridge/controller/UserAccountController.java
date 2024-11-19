package com.backend.autocarrerbridge.controller;

import com.backend.autocarrerbridge.dto.*;
import com.backend.autocarrerbridge.entity.UserAccount;
import com.backend.autocarrerbridge.model.api.ApiResponse;
import com.backend.autocarrerbridge.model.api.AuthenticationResponse;
import com.backend.autocarrerbridge.service.AuthenticationService;
import com.backend.autocarrerbridge.service.UserAccountService;
import com.backend.autocarrerbridge.service.UserAuthentication;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/accounts")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final AuthenticationService authenticationService;
    private final UserAuthentication userAuthentication;
    @PostMapping("/register")
    private ResponseEntity<?> registerBussiness(@ModelAttribute  UserBussinessDTO userBussinessDTO){
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Success");
        apiResponse.setCode(200);
        apiResponse.setData(userAccountService.registerBussiness(userBussinessDTO));
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/register/uni")
    private ResponseEntity<?> registerUniversity(@RequestBody  UserUniversityDTO userUniversityDTO){
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Success");
        apiResponse.setCode(200);
        apiResponse.setData(userAccountService.registerUniversity(userUniversityDTO));
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginJWT(@RequestBody UserAccountResponeDTO accountDTO) throws ParseException {
        DisplayUserAccountDTO useraccountDTO = userAccountService.login(accountDTO);
        AuthenticationResponse authenticationResponse =  userAuthentication.authenticate(accountDTO.getUsername());
        useraccountDTO.setAuthenticationResponse(authenticationResponse);
        ApiResponse<DisplayUserAccountDTO> apiResponse = new ApiResponse<>();
        apiResponse.setData(useraccountDTO);
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage("Success");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) throws ParseException {
        String token = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            token = authorizationHeader.substring(7);
        }
        authenticationService.logout(token);
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setData("Logout and add to blacklist");
        apiResponse.setCode(HttpStatus.OK.value());
        apiResponse.setMessage("Logout Success");
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody String token) throws ParseException {
        String newToken = authenticationService.getNewToken(token);
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("Refresh Token Success");
        apiResponse.setData(newToken);
        return ResponseEntity.ok(apiResponse);
    }
}
