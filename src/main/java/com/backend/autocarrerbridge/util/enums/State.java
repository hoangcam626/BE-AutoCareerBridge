package com.backend.autocarrerbridge.util.enums;

import lombok.Getter;

@Getter
public enum         State {
    PENDING(0),
    APPROVED(1),
    REJECTED(2);

    private final int value;

    State(int value) {
        this.value = value;
    }
}
