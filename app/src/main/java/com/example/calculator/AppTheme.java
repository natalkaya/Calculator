package com.example.calculator;

public enum AppTheme {
    DEFAULT(0, R.style.Theme_Calculator),
    BLUE(1, R.style.Theme_Calculator_Blue);

    private final int number;
    private final int codeStyleId;

    public int getCodeStyleId() {
        return codeStyleId;
    }

    AppTheme(int number, int codeStyleId) {
        this.number = number;
        this.codeStyleId = codeStyleId;
    }

    public int getNumber() {
        return number;
    }

    public static AppTheme defineBy(int number) {
        switch (number) {
            case 0:
                return DEFAULT;
            case 1:
                return BLUE;
            default:
                throw new IllegalStateException("Unexpected value: " + number);
        }
    }

}
