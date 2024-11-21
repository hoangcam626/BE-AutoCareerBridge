package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.AccountRespone.DisplayBusinessDTO;
import com.backend.autocarrerbridge.dto.AccountRespone.UserBusinessDTO;
import com.backend.autocarrerbridge.entity.Business;

public interface BusinessService {
    DisplayBusinessDTO registerBusiness(UserBusinessDTO userBusinessDTO);
    Business findByEmail(String email);

}
