package com.backend.autocarrerbridge.util.enums;

import lombok.Getter;

@Getter
public enum StatusRead {
    UNREAD(0),
    READ(1);

    private final int value;

    StatusRead(int value) {
        this.value = value;
    }
}
