package com.yourcompany.bank.core.card;

import com.yourcompany.bank.card.impl.DebitCard;
import com.yourcompany.bank.common.ErrorCode;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Debit Card")
@DisplayName("Тесты дебетовой карты")
class DebitCardTest {

    private static final Currency USD = Currency.getInstance("USD");

    @Test
    @DisplayName("Пополнение дебетовой карты")
    @Severity(SeverityLevel.CRITICAL)
    void testDebitCardDeposit() {
        var card = new DebitCard("Basic Debit", USD, BigDecimal.ZERO);
        card.deposit(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), card.getBalance());
    }

    @Test
    @DisplayName("Списание с дебетовой карты")
    @Severity(SeverityLevel.CRITICAL)
    void testDebitCardWithdraw() {
        var card = new DebitCard("Basic Debit", USD, BigDecimal.valueOf(200));
        card.withdraw(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(150), card.getBalance());
    }

    @ParameterizedTest(name = "Пополнение недопустимым значением: {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"0", "-1", "-0.01"})
    @Severity(SeverityLevel.NORMAL)
    void testInvalidDepositThrows(String amountStr) {
        var card = new DebitCard("Basic Debit", USD, BigDecimal.TEN);
        BigDecimal amount = (amountStr == null || amountStr.isEmpty()) ? null : new BigDecimal(amountStr);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> card.deposit(amount));
        assertEquals(ErrorCode.AMOUNT_MUST_BE_POSITIVE.message(), ex.getMessage());
    }

    @ParameterizedTest(name = "Списание недопустимым значением: {0}")
    @NullAndEmptySource
    @ValueSource(strings = {"0", "-1", "-0.01"})
    @Severity(SeverityLevel.NORMAL)
    void testInvalidWithdrawThrows(String amountStr) {
        var card = new DebitCard("Basic Debit", USD, BigDecimal.TEN);
        BigDecimal amount = (amountStr == null || amountStr.isEmpty()) ? null : new BigDecimal(amountStr);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> card.withdraw(amount));
        assertEquals(ErrorCode.AMOUNT_MUST_BE_POSITIVE.message(), ex.getMessage());
    }

    @Test
    @DisplayName("Списание больше баланса вызывает исключение")
    @Severity(SeverityLevel.CRITICAL)
    void testWithdrawMoreThanBalanceThrows() {
        var card = new DebitCard("Basic Debit", USD, BigDecimal.valueOf(100));
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> card.withdraw(BigDecimal.valueOf(150)));
        assertEquals(ErrorCode.INSUFFICIENT_BALANCE.message(), ex.getMessage());
    }
}
