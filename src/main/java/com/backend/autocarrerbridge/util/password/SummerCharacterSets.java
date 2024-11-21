package com.backend.autocarrerbridge.util.password;

import static com.backend.autocarrerbridge.util.password.CharacterSet.*;

public enum SummerCharacterSets implements PasswordCharacterSet {
    ALPHA_UPPER(ALPHA_UPPER_CHARACTERS, 1),
    ALPHA_LOWER(ALPHA_LOWER_CHARACTERS, 1),
    NUMERIC(NUMERIC_CHARACTERS, 1),
    SPECIAL(SPECIAL_CHARACTERS, 1);

    private final char[] chars;
    private final int minUsage;

    SummerCharacterSets(char[] chars, int minUsage) {
        this.chars = chars;
        this.minUsage = minUsage;
    }

    public char[] getCharacters() {
        return chars;
    }

    public int getMinCharacters() {
        return minUsage;
    }
}
