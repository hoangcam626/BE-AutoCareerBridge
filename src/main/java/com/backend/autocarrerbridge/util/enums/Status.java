package com.backend.autocarrerbridge.util.enums;

import lombok.Getter;

@Getter
public enum Status {
    INACTIVE(0),
    ACTIVE(1);

    private final int value;

    Status(int value) {
        this.value = value;
    }
}
