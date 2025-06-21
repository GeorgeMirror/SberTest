package com.yourcompany.bank.deposit.impl;

import com.yourcompany.bank.deposit.abs.AbstractDeposit;

import java.math.BigDecimal;
import java.util.Currency;

public class Deposit extends AbstractDeposit {

    public Deposit(String name, Currency currency, BigDecimal balance) {
        super(name, currency, balance);
    }
}
