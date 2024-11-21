package com.backend.autocarrerbridge.service.impl;

import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.repository.BussinessRepository;
import com.backend.autocarrerbridge.service.BusinessService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BusinessServiceImpl implements BusinessService {
    BussinessRepository bussinessRepository;

    @Override
    public Business findBusinessByEmail(String email) {
        return bussinessRepository.findByEmail(email);
    }

    @Override
    public Business updateBusiness(Integer id, Business business) {
        return null;
    }

    @Override
    public List<Business> getListBusiness() {
        return List.of();
    }

    @Override
    public Business getBusinessById(Integer id) {
        return null;
    }

    @Override
    public Business addBusiness(Business request) {
        return null;
    }

    @Override
    public void deleteBusiness(Integer id) {

    }
}
