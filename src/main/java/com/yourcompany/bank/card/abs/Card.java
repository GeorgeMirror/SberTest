package com.yourcompany.bank.card.abs;

import com.yourcompany.bank.core.AbstractBankProduct;
import com.yourcompany.bank.interfaces.Replenishable;
import com.yourcompany.bank.interfaces.Withdrawable;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class Card extends AbstractBankProduct implements Replenishable, Withdrawable {
    protected Card(String name, Currency currency, BigDecimal initialBalance) {
        super(name, currency, initialBalance);
    }

    @Override
    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }
}
