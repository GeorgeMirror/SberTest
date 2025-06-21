package com.yourcompany.bank.interfaces;

import java.math.BigDecimal;
import java.util.Currency;

public interface BankProduct {
    String getName();
    Currency getCurrency();
    BigDecimal getBalance();
}
