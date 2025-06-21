package com.yourcompany.bank.card.impl;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyDebitCard extends DebitCard {
    public CurrencyDebitCard(String name, Currency currency, BigDecimal initialBalance) {
        super(name, currency, initialBalance);
    }
}
