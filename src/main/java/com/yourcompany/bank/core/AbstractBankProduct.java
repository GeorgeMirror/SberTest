package com.yourcompany.bank.core;

import com.yourcompany.bank.interfaces.BankProduct;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class AbstractBankProduct implements BankProduct {
    protected final String name;
    protected final Currency currency;
    protected BigDecimal balance;

    protected AbstractBankProduct(String name, Currency currency, BigDecimal initialBalance) {
        this.name = name;
        this.currency = currency;
        this.balance = initialBalance;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }
}
