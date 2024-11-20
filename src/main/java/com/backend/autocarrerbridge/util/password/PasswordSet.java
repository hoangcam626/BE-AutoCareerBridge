package com.backend.autocarrerbridge.util.password;

import java.util.Arrays;

public class PasswordSet implements PasswordCharacterSet {
    private char[] chars;
    private int minChars;
    public PasswordSet(PasswordCharacterSet pwSet) {
        this.minChars = pwSet.getMinCharacters();
        char[] pwSetChars = pwSet.getCharacters();
        this.chars = Arrays.copyOf(pwSetChars, pwSetChars.length);
    }
    public char[] getCharacters() {
        return chars;
    }
    public int getMinCharacters() {
        return minChars;
    }
}

