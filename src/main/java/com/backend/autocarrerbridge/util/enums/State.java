package com.backend.autocarrerbridge.util.enums;

import lombok.Getter;

@Getter
public enum State {
    APPROVED(1), PENDING(0), REJECTED(2);

    private final int value;

    State(int value) {
        this.value = value;
    }
}
