package com.yourcompany.bank.core.deposit;

import com.yourcompany.bank.core.AbstractBankProduct;
import com.yourcompany.bank.interfaces.Closable;
import com.yourcompany.bank.interfaces.Replenishable;

import java.math.BigDecimal;
import java.util.Currency;

public class Deposit extends AbstractBankProduct implements Replenishable, Closable {

    private boolean closed = false;

    public Deposit(String name, Currency currency, BigDecimal initialBalance) {
        super(name, currency, initialBalance);
    }

    @Override
    public void deposit(BigDecimal amount) {
        if (closed) throw new IllegalStateException("Deposit is closed");
        balance = balance.add(amount);
    }

    @Override
    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
