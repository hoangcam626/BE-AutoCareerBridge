package com.backend.autocarrerbridge.mapper;

import org.mapstruct.Mapper;

import com.backend.autocarrerbridge.dto.request.useraccount.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.account.UserAccountResponse;
import com.backend.autocarrerbridge.entity.UserAccount;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
    UserAccount toUserAccount(UserAccountRequest request);

    UserAccountResponse toUserAccountResponse(UserAccount userAccount);

    UserAccount toUserAccount(UserAccountResponse response);
}
