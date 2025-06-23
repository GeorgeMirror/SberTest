package com.yourcompany.bank.core;

import com.yourcompany.bank.card.abs.Card;
import com.yourcompany.bank.card.impl.CreditCard;
import com.yourcompany.bank.card.impl.CurrencyDebitCard;
import com.yourcompany.bank.card.impl.DebitCard;
import com.yourcompany.bank.deposit.impl.Deposit;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Card Functionality")
@DisplayName("Тесты для банковских продуктов (карты и вклады)")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class BankProductFullTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");

    @Nested
    @DisplayName("1. Тесты дебетовой карты")
    class DebitCardTests {

        @Test
        @DisplayName("1.1 Пополнение дебетовой карты")
        @Severity(SeverityLevel.CRITICAL)
        void testDebitCardDeposit() {
            var card = new DebitCard("Basic Debit", USD, BigDecimal.ZERO);
            card.deposit(BigDecimal.valueOf(100));
            assertEquals(BigDecimal.valueOf(100), card.getBalance());
        }

        @Test
        @DisplayName("1.2 Списание с дебетовой карты")
        @Severity(SeverityLevel.CRITICAL)
        void testDebitCardWithdraw() {
            var card = new DebitCard("Basic Debit", USD, BigDecimal.valueOf(200));
            card.withdraw(BigDecimal.valueOf(50));
            assertEquals(BigDecimal.valueOf(150), card.getBalance());
        }
    }

    @Nested
    @DisplayName("2. Тесты валютной дебетовой карты")
    class CurrencyDebitCardTests {

        @Test
        @DisplayName("2.1 Поведение валютной карты при операциях")
        @Severity(SeverityLevel.NORMAL)
        void testCurrencyCardBehavior() {
            var card = new CurrencyDebitCard("EUR Debit", EUR, BigDecimal.valueOf(300));
            card.deposit(BigDecimal.valueOf(100));
            card.withdraw(BigDecimal.valueOf(50));
            assertEquals(BigDecimal.valueOf(350), card.getBalance());
        }
    }

    @Nested
    @DisplayName("3. Тесты кредитной карты")
    class CreditCardTests {

        @Test
        @DisplayName("3.1 Списание в минус (задолженность)")
        @Severity(SeverityLevel.BLOCKER)
        void testCreditCardDebt() {
            var card = new CreditCard("Credit", USD, BigDecimal.ZERO, BigDecimal.valueOf(20));
            card.withdraw(BigDecimal.valueOf(500));
            assertEquals(BigDecimal.valueOf(-500), card.getBalance());
            assertEquals(BigDecimal.valueOf(500), card.getDebt());
        }

        @Test
        @DisplayName("3.2 Получение процентной ставки")
        @Severity(SeverityLevel.MINOR)
        void testCreditCardInterest() {
            var card = new CreditCard("Credit", USD, BigDecimal.TEN, BigDecimal.valueOf(19.99));
            assertEquals(BigDecimal.valueOf(19.99), card.getInterestRate());
        }
    }

    @Nested
    @DisplayName("4. Тесты вклада")
    class DepositTests {

        @Test
        @DisplayName("4.1 Пополнение вклада")
        @Severity(SeverityLevel.CRITICAL)
        void testDepositTopUp() {
            var deposit = new Deposit("Standard", EUR, BigDecimal.valueOf(1000));
            deposit.deposit(BigDecimal.valueOf(500));
            assertEquals(BigDecimal.valueOf(1500), deposit.getBalance());
        }

        @Test
        @DisplayName("4.2 Закрытие вклада")
        @Severity(SeverityLevel.NORMAL)
        void testDepositClose() {
            var deposit = new Deposit("Standard", EUR, BigDecimal.valueOf(1000));
            deposit.close();
            assertTrue(deposit.isClosed());
        }

        @Test
        @DisplayName("4.3 Ошибка пополнения закрытого вклада")
        @Severity(SeverityLevel.CRITICAL)
        void testDepositAfterCloseFails() {
            var deposit = new Deposit("Standard", EUR, BigDecimal.valueOf(1000));
            deposit.close();
            assertThrows(IllegalStateException.class, () -> deposit.deposit(BigDecimal.valueOf(100)));
        }
    }

    @Nested
    @DisplayName("5. Базовый класс Card")
    class AbstractCardTests {

        static class TestCard extends Card {
            public TestCard(String name, Currency currency, BigDecimal balance) {
                super(name, currency, balance);
            }
        }

        @Test
        @DisplayName("5.1 Общая логика пополнения/списания")
        @Severity(SeverityLevel.NORMAL)
        void testGenericCardBehavior() {
            var card = new TestCard("Test Card", USD, BigDecimal.valueOf(100));
            card.deposit(BigDecimal.valueOf(100));
            card.withdraw(BigDecimal.valueOf(50));
            assertEquals(BigDecimal.valueOf(150), card.getBalance());
        }
    }
}
