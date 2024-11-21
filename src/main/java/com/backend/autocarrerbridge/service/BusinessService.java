package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.AccountRespone.DisplayBusinessDTO;
import com.backend.autocarrerbridge.dto.AccountRespone.UserBusinessDTO;
import com.backend.autocarrerbridge.entity.Business;

import java.util.List;

public interface BusinessService {
    DisplayBusinessDTO registerBusiness(UserBusinessDTO userBusinessDTO);
    Business findByEmail(String email);
    Business updateBusiness(Integer id, Business business);
    List<Business> getListBusiness();
    Business getBusinessById(Integer id);
    Business addBusiness(Business request);
    void deleteBusiness(Integer id);
}
