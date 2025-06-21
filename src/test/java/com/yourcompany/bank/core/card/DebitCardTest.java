package com.yourcompany.bank.core.card;

import com.yourcompany.bank.card.impl.DebitCard;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Debit Card")
@DisplayName("Тесты дебетовой карты")
class DebitCardTest {

    @Test
    @DisplayName("Пополнение дебетовой карты")
    @Severity(SeverityLevel.CRITICAL)
    void testDebitCardDeposit() {
        var card = new DebitCard("Basic Debit", Currency.getInstance("USD"), BigDecimal.ZERO);
        card.deposit(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), card.getBalance());
    }

    @Test
    @DisplayName("Списание с дебетовой карты")
    @Severity(SeverityLevel.CRITICAL)
    void testDebitCardWithdraw() {
        var card = new DebitCard("Basic Debit", Currency.getInstance("USD"), BigDecimal.valueOf(200));
        card.withdraw(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), card.getBalance());
    }
}
