package com.yourcompany.bank.common;

public enum ErrorCode {
    AMOUNT_MUST_BE_POSITIVE("Amount must be positive"),
    INSUFFICIENT_BALANCE("Insufficient balance"),
    DEPOSIT_CLOSED("Cannot deposit to a closed deposit");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
