package com.backend.autocarrerbridge.util.enums;

import lombok.Getter;

@Getter
public enum OrganizationType {
    UNIVERSITY(2),
    BUSINESS(1);

    private final int value;

    OrganizationType(int value) {
        this.value = value;
    }

}