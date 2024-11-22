package com.backend.autocarrerbridge.service;

import java.util.List;


import com.backend.autocarrerbridge.dto.response.business.BusinessRegisterResponse;
import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.entity.Business;

public interface BusinessService {
    BusinessRegisterResponse registerBusiness(UserBusinessRequest userBusinessRequest);

    Business findByEmail(String email);

    Business updateBusiness(Integer id, Business business);

    List<Business> getListBusiness();

    Business getBusinessById(Integer id);

    Business addBusiness(Business request);

    void deleteBusiness(Integer id);
}
