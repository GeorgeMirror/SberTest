package com.yourcompany.bank.core.card;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Card Base Functionality")
@DisplayName("Тест абстрактной карточки")
class CardAbstractTest {

    static class TestCard extends Card {
        public TestCard(String name, Currency currency, BigDecimal balance) {
            super(name, currency, balance);
        }
    }

    @Test
    @DisplayName("Базовая логика пополнения/списания")
    @Severity(SeverityLevel.NORMAL)
    void testGenericCardBehavior() {
        var card = new TestCard("Test Card", Currency.getInstance("USD"), BigDecimal.valueOf(100));
        card.deposit(BigDecimal.valueOf(100));
        card.withdraw(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), card.getBalance());
    }
}
