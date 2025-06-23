package com.yourcompany.bank.core.card;

import com.yourcompany.bank.card.impl.CurrencyDebitCard;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Currency Debit Card")
@DisplayName("Тесты валютной дебетовой карты")
class CurrencyDebitCardTest {

    private static final Currency EUR = Currency.getInstance("EUR");

    @ParameterizedTest(name = "Баланс: {0}, +{1}, -{2} → Ожидаем: {3}")
    @CsvSource({
            "300, 100, 50, 350",
            "100, 200, 100, 200",
            "0, 100, 50, 50"
    })
    @Severity(SeverityLevel.NORMAL)
    void testCurrencyCardBehavior(String initial, String deposit, String withdraw, String expected) {
        var card = new CurrencyDebitCard("EUR Debit", EUR, new BigDecimal(initial));
        card.deposit(new BigDecimal(deposit));
        card.withdraw(new BigDecimal(withdraw));
        assertEquals(new BigDecimal(expected), card.getBalance());
    }
}
