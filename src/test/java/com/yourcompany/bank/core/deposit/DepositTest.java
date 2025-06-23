package com.yourcompany.bank.core.deposit;

import com.yourcompany.bank.deposit.impl.Deposit;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Deposit")
@DisplayName("Тесты вклада")
class DepositTest {

    private static final Currency EUR = Currency.getInstance("EUR");

    @ParameterizedTest(name = "Начальный баланс {0}, пополнение {1}, ожидаемый итог {2}")
    @CsvSource({
            "1000, 500, 1500",
            "500, 250, 750",
            "0, 100, 100"
    })
    @Severity(SeverityLevel.CRITICAL)
    void testDepositTopUp(String initial, String add, String expected) {
        var deposit = new Deposit("Standard", EUR, new BigDecimal(initial));
        deposit.deposit(new BigDecimal(add));
        assertEquals(new BigDecimal(expected), deposit.getBalance());
    }

    @Test
    @DisplayName("Закрытие вклада")
    @Severity(SeverityLevel.NORMAL)
    void testDepositClose() {
        var deposit = new Deposit("Standard", EUR, BigDecimal.valueOf(1000));
        deposit.close();
        assertTrue(deposit.isClosed());
    }

    @Test
    @DisplayName("Ошибка пополнения закрытого вклада")
    @Severity(SeverityLevel.CRITICAL)
    void testDepositAfterCloseFails() {
        var deposit = new Deposit("Standard", EUR, BigDecimal.valueOf(1000));
        deposit.close();
        assertThrows(IllegalStateException.class, () -> deposit.deposit(BigDecimal.valueOf(100)));
    }
}
