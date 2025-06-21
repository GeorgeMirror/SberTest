package com.yourcompany.bank.core.card;

import com.yourcompany.bank.card.impl.CreditCard;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Credit Card")
@DisplayName("Тесты кредитной карты")
class CreditCardTest {

    @Test
    @DisplayName("Списание в минус (задолженность)")
    @Severity(SeverityLevel.BLOCKER)
    void testCreditCardDebt() {
        var card = new CreditCard("Credit", Currency.getInstance("USD"), BigDecimal.ZERO, BigDecimal.valueOf(20));
        card.withdraw(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(-500), card.getBalance());
        assertEquals(BigDecimal.valueOf(500), card.getDebt());
    }

    @Test
    @DisplayName("Получение процентной ставки")
    @Severity(SeverityLevel.MINOR)
    void testCreditCardInterest() {
        var card = new CreditCard("Credit", Currency.getInstance("USD"), BigDecimal.TEN, BigDecimal.valueOf(19.99));
        assertEquals(BigDecimal.valueOf(19.99), card.getInterestRate());
    }
}
