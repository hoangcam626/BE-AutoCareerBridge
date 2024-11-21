package com.backend.autocarrerbridge.mapper;

import com.backend.autocarrerbridge.dto.request.UserAccountRequest;
import com.backend.autocarrerbridge.dto.response.UserAccountResponse;
import com.backend.autocarrerbridge.entity.UserAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
    UserAccount toUserAccount(UserAccountRequest request);

    UserAccountResponse toUserAccountResponse(UserAccount userAccount);

    UserAccount toUserAccount(UserAccountResponse response);
}
