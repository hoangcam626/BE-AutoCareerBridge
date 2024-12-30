package com.backend.autocarrerbridge.dto.response.cooperation;

import com.backend.autocarrerbridge.dto.response.abstractaudit.AbstractAuditResponse;
import com.backend.autocarrerbridge.dto.response.business.BusinessResponse;
import com.backend.autocarrerbridge.util.enums.State;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class CooperationUniversityResponse extends AbstractAuditResponse {
    Integer id;

    State statusConnected;

    BusinessResponse business;
}
