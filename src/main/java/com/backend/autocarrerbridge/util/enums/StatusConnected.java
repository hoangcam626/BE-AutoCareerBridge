package com.backend.autocarrerbridge.util.enums;

import lombok.Getter;

@Getter
public enum StatusConnected {
    APPROVED(1), PENDING(0), REJECTED(2);

    private final int value;

    StatusConnected(int value) {
        this.value = value;
    }
}
