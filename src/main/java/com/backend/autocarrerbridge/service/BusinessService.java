package com.backend.autocarrerbridge.service;

import java.util.List;

import com.backend.autocarrerbridge.dto.request.account.UserBusinessRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessApprovedRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessRejectedRequest;
import com.backend.autocarrerbridge.dto.request.business.BusinessUpdateRequest;
import com.backend.autocarrerbridge.dto.request.page.PageInfo;
import com.backend.autocarrerbridge.dto.response.business.BusinessApprovedResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessRegisterResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessRejectedResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.entity.Business;
import com.backend.autocarrerbridge.util.email.EmailCode;
import org.springframework.data.domain.Page;

public interface BusinessService {
    BusinessRegisterResponse registerBusiness(UserBusinessRequest userBusinessRequest);

    Business findByEmail(String email);

    BusinessResponse updateBusiness(Integer id, BusinessUpdateRequest request);

    List<BusinessResponse> getListBusiness();

    Business getBusinessById(Integer id);

    BusinessResponse getBusinessResponseById(Integer id);

    void deleteBusiness(Integer id);

    BusinessApprovedResponse approvedAccount(BusinessApprovedRequest req);

    BusinessRejectedResponse rejectedAccount(BusinessRejectedRequest req);

    EmailCode generateEmailCode(UserBusinessRequest userBusinessRequest);

    Page<BusinessResponse> getPagingByState(PageInfo req, Integer state );

}
