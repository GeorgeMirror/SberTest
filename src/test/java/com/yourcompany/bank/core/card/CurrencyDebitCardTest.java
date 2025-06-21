package com.yourcompany.bank.core.card;

import com.yourcompany.bank.card.impl.CurrencyDebitCard;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Currency Debit Card")
@DisplayName("Тесты валютной дебетовой карты")
class CurrencyDebitCardTest {

    @Test
    @DisplayName("Поведение валютной карты при операциях")
    @Severity(SeverityLevel.NORMAL)
    void testCurrencyCardBehavior() {
        var card = new CurrencyDebitCard("EUR Debit", Currency.getInstance("EUR"), BigDecimal.valueOf(300));
        card.deposit(BigDecimal.valueOf(100));
        card.withdraw(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(350), card.getBalance());
    }
}
