package com.example.calculator;

public enum Operations {
    PLUS("+") {
        @Override
        public double calculate(double newValue, double currentResult) {
            currentResult += newValue;
            return currentResult;
        }
    },
    MINUS("-") {
        @Override
        public double calculate(double newValue, double currentResult) {
            currentResult -= newValue;
            return currentResult;
        }
    }
    // todo add all operations
    ;

    String value;

    Operations(String value) {
        this.value = value;
    }

    public abstract double calculate(double newValue, double currentResult);
}
