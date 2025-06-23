package com.yourcompany.bank.card.impl;

import com.yourcompany.bank.card.abs.Card;

import java.math.BigDecimal;
import java.util.Currency;

public class CreditCard extends Card {
    private final BigDecimal interestRate;

    public CreditCard(String name, Currency currency, BigDecimal initialBalance, BigDecimal interestRate) {
        super(name, currency, initialBalance);
        this.interestRate = interestRate;
    }

    @Override
    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal debt = balance.abs();
            if (amount.compareTo(debt) >= 0) {
                balance = amount.subtract(debt);
            } else {
                balance = balance.add(amount);
            }
        } else {
            balance = balance.add(amount);
        }
    }

    @Override
    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        balance = balance.subtract(amount);
    }

    public BigDecimal getDebt() {
        return balance.compareTo(BigDecimal.ZERO) < 0 ? balance.abs() : BigDecimal.ZERO;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }
}
