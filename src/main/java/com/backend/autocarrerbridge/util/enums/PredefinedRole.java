package com.backend.autocarrerbridge.util.enums;

import lombok.Getter;

@Getter
public enum PredefinedRole {
    ADMIN(1),
    BUSINESS(2),
    UNIVERSITY(3),
    EMPLOYEE(4),
    SUB_ADMIN(5);

    private final int value;

    PredefinedRole(int value) {
        this.value = value;
    }
}
