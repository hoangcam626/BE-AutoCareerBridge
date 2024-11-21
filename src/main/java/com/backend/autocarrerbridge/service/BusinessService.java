package com.backend.autocarrerbridge.service;


import com.backend.autocarrerbridge.dto.request.EmployeeRequest;
import com.backend.autocarrerbridge.dto.response.EmployeeResponse;
import com.backend.autocarrerbridge.entity.Business;

import java.util.List;

public interface BusinessService {
    Business findBusinessByEmail(String email);
    Business updateBusiness(Integer id, Business business);
    List<Business> getListBusiness();
    Business getBusinessById(Integer id);
    Business addBusiness(Business request);
    void deleteBusiness(Integer id);
}
