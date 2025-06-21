package com.yourcompany.bank.card.impl;

import com.yourcompany.bank.card.abs.Card;

import java.math.BigDecimal;
import java.util.Currency;

public class DebitCard extends Card {
    public DebitCard(String name, Currency currency, BigDecimal initialBalance) {
        super(name, currency, initialBalance);
    }
}
