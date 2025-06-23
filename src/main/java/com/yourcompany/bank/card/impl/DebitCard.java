package com.yourcompany.bank.card.impl;

import com.yourcompany.bank.card.abs.Card;
import com.yourcompany.bank.common.ErrorCode;

import java.math.BigDecimal;
import java.util.Currency;

public class DebitCard extends Card {
    public DebitCard(String name, Currency currency, BigDecimal initialBalance) {
        super(name, currency, initialBalance);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException(ErrorCode.INSUFFICIENT_BALANCE.message());
        }
        balance = balance.subtract(amount);
    }
}
