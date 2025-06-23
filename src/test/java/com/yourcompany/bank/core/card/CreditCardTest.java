package com.yourcompany.bank.core.card;

import com.yourcompany.bank.card.impl.CreditCard;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Credit Card")
@DisplayName("Параметризованные тесты кредитной карты")
class CreditCardTest {

    private static final Currency USD = Currency.getInstance("USD");

    @ParameterizedTest(name = "Начальный баланс={0}, списать={1}, пополнить={2}, ожидаемый баланс={3}, ожидаемый долг={4}")
    @CsvSource({
            "0, 500, 600, 100, 0",     // погасили долг и получаем положительный баланс
            "0, 500, 400, -100, 100",  // погасили часть долга, долг остался 100
            "1000, 500, 200, 700, 0",  // списали меньше, пополнили, итог положительный баланс
            "0, 0, 500, 500, 0",       // просто пополнение без долга
            "100, 200, 100, 0, 0"      // баланс стал отрицательным -100, долг 100
    })
    @Severity(SeverityLevel.NORMAL)
    void testCreditCardTransactions(
            String initialBalanceStr, String withdrawStr, String depositStr,
            String expectedBalanceStr, String expectedDebtStr
    ) {
        BigDecimal initialBalance = new BigDecimal(initialBalanceStr);
        BigDecimal withdrawAmount = new BigDecimal(withdrawStr);
        BigDecimal depositAmount = new BigDecimal(depositStr);
        BigDecimal expectedBalance = new BigDecimal(expectedBalanceStr);
        BigDecimal expectedDebt = new BigDecimal(expectedDebtStr);

        var card = new CreditCard("Credit", USD, initialBalance, BigDecimal.valueOf(20));  // используем USD

        if (withdrawAmount.compareTo(BigDecimal.ZERO) > 0) {
            card.withdraw(withdrawAmount);
        }
        if (depositAmount.compareTo(BigDecimal.ZERO) > 0) {
            card.deposit(depositAmount);
        }

        assertEquals(0, card.getBalance().compareTo(expectedBalance),
                () -> "Баланс не совпадает: ожидалось " + expectedBalance + ", было " + card.getBalance());
        assertEquals(0, card.getDebt().compareTo(expectedDebt),
                () -> "Долг не совпадает: ожидалось " + expectedDebt + ", было " + card.getDebt());
    }
}
