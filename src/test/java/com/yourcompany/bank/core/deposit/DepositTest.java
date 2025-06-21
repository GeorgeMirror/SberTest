package com.yourcompany.bank.core.deposit;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Deposit")
@DisplayName("Тесты вклада")
class DepositTest {

    @Test
    @DisplayName("Пополнение вклада")
    @Severity(SeverityLevel.CRITICAL)
    void testDepositTopUp() {
        var deposit = new Deposit("Standard", Currency.getInstance("EUR"), BigDecimal.valueOf(1000));
        deposit.deposit(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.valueOf(1500), deposit.getBalance());
    }

    @Test
    @DisplayName("Закрытие вклада")
    @Severity(SeverityLevel.NORMAL)
    void testDepositClose() {
        var deposit = new Deposit("Standard", Currency.getInstance("EUR"), BigDecimal.valueOf(1000));
        deposit.close();
        assertTrue(deposit.isClosed());
    }

    @Test
    @DisplayName("Ошибка пополнения закрытого вклада")
    @Severity(SeverityLevel.CRITICAL)
    void testDepositAfterCloseFails() {
        var deposit = new Deposit("Standard", Currency.getInstance("EUR"), BigDecimal.valueOf(1000));
        deposit.close();
        assertThrows(IllegalStateException.class, () -> deposit.deposit(BigDecimal.valueOf(100)));
    }
}
