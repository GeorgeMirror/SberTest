package com.yourcompany.bank.deposit.abs;

import com.yourcompany.bank.interfaces.BankProduct;
import com.yourcompany.bank.interfaces.Replenishable;
import com.yourcompany.bank.interfaces.Closable;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class AbstractDeposit implements BankProduct, Replenishable, Closable {
    protected String name;
    protected Currency currency;
    protected BigDecimal balance;
    protected boolean closed = false;

    protected AbstractDeposit(String name, Currency currency, BigDecimal balance) {
        this.name = name;
        this.currency = currency;
        this.balance = balance;
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

    @Override
    public void deposit(BigDecimal amount) {
        if (closed) {
            throw new IllegalStateException("Cannot deposit to a closed deposit");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        balance = balance.add(amount);
    }

    @Override
    public void close() {
        if (closed) {
            throw new IllegalStateException("Deposit is already closed");
        }
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
