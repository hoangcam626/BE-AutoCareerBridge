package com.backend.autocarrerbridge.service;

import com.backend.autocarrerbridge.dto.PageInfoDTO;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminCreateSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminDeleteSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminSelfSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdi.SubAdminUpdateSdi;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminCreateSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminDeleteSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminSelfSdo;
import com.backend.autocarrerbridge.dto.subadmin.sdo.SubAdminUpdateSdo;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface SubAdminService {
    SubAdminCreateSdo create(SubAdminCreateSdi req) throws ParseException;

    SubAdminUpdateSdo update(SubAdminUpdateSdi req) throws ParseException;

    SubAdminSelfSdo self(SubAdminSelfSdi req);

    SubAdminDeleteSdo delete(SubAdminDeleteSdi req);

    Page<SubAdminSelfSdo> pageSubAdmins(PageInfoDTO req);

    List<SubAdminSelfSdo> listSubAdmins();
}
