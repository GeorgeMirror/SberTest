package com.yourcompany.bank.core;

import com.yourcompany.bank.card.abs.Card;
import com.yourcompany.bank.card.impl.*;
import com.yourcompany.bank.deposit.impl.Deposit;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Bank Products")
@Feature("Card and Deposit Functionality")
@DisplayName("Интеграционные тесты банковских продуктов (карты и вклады)")
@TestMethodOrder(MethodOrderer.DisplayName.class)
class BankProductFullTest {

    @Nested
    @DisplayName("1. Дебетовая карта")
    class DebitCardTests {

        @Test
        @DisplayName("1.1 Пополнение")
        @Severity(SeverityLevel.CRITICAL)
        void deposit() {
            var card = new DebitCard("Basic Debit", Currency.getInstance("USD"), BigDecimal.ZERO);
            card.deposit(BigDecimal.valueOf(100));
            assertEquals(BigDecimal.valueOf(100), card.getBalance());
        }

        @Test
        @DisplayName("1.2 Списание")
        @Severity(SeverityLevel.CRITICAL)
        void withdraw() {
            var card = new DebitCard("Basic Debit", Currency.getInstance("USD"), BigDecimal.valueOf(200));
            card.withdraw(BigDecimal.valueOf(50));
            assertEquals(BigDecimal.valueOf(150), card.getBalance());
        }

        @ParameterizedTest(name = "Недопустимое пополнение: {0}")
        @CsvSource({"0", "-1", "-0.01"})
        @Severity(SeverityLevel.NORMAL)
        void invalidDepositThrows(String amountStr) {
            var card = new DebitCard("Basic Debit", Currency.getInstance("USD"), BigDecimal.TEN);
            BigDecimal amount = new BigDecimal(amountStr);
            var ex = assertThrows(IllegalArgumentException.class, () -> card.deposit(amount));
            assertTrue(ex.getMessage().contains("Amount must be positive"));
        }

        @ParameterizedTest(name = "Недопустимое списание: {0}")
        @CsvSource({"0", "-1", "-0.01"})
        @Severity(SeverityLevel.NORMAL)
        void invalidWithdrawThrows(String amountStr) {
            var card = new DebitCard("Basic Debit", Currency.getInstance("USD"), BigDecimal.TEN);
            BigDecimal amount = new BigDecimal(amountStr);
            var ex = assertThrows(IllegalArgumentException.class, () -> card.withdraw(amount));
            assertTrue(ex.getMessage().contains("Amount must be positive"));
        }

        @Test
        @DisplayName("1.3 Списание больше баланса")
        @Severity(SeverityLevel.CRITICAL)
        void withdrawMoreThanBalanceThrows() {
            var card = new DebitCard("Basic Debit", Currency.getInstance("USD"), BigDecimal.valueOf(100));
            var ex = assertThrows(IllegalArgumentException.class, () -> card.withdraw(BigDecimal.valueOf(150)));
            assertTrue(ex.getMessage().contains("Insufficient balance"));
        }
    }

    @Nested
    @DisplayName("2. Валютная дебетовая карта")
    class CurrencyDebitCardTests {

        @ParameterizedTest(name = "Баланс {0}, +{1}, -{2} → ожидаем {3}")
        @CsvSource({
                "300, 100, 50, 350",
                "100, 200, 100, 200",
                "0, 100, 50, 50"
        })
        @Severity(SeverityLevel.NORMAL)
        void testCurrencyCardBehavior(String initial, String deposit, String withdraw, String expected) {
            var card = new CurrencyDebitCard("EUR Debit", Currency.getInstance("EUR"), new BigDecimal(initial));
            card.deposit(new BigDecimal(deposit));
            card.withdraw(new BigDecimal(withdraw));
            assertEquals(new BigDecimal(expected), card.getBalance());
        }
    }

    @Nested
    @DisplayName("3. Кредитная карта")
    class CreditCardTests {

        @ParameterizedTest(name = "Начальный баланс={0}, списать={1}, пополнить={2}, ожидаемый баланс={3}, долг={4}")
        @CsvSource({
                "0, 500, 600, 100, 0",
                "0, 500, 400, -100, 100",
                "1000, 500, 200, 700, 0",
                "0, 0, 500, 500, 0",
                "100, 200, 100, 0, 0"
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

            var card = new CreditCard("Credit", Currency.getInstance("USD"), initialBalance, BigDecimal.valueOf(20));

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

        @Test
        @DisplayName("3.2 Получение процентной ставки")
        @Severity(SeverityLevel.MINOR)
        void testCreditCardInterest() {
            var card = new CreditCard("Credit", Currency.getInstance("USD"), BigDecimal.TEN, BigDecimal.valueOf(19.99));
            assertEquals(BigDecimal.valueOf(19.99), card.getInterestRate());
        }
    }

    @Nested
    @DisplayName("4. Вклад")
    class DepositTests {

        @ParameterizedTest(name = "Начальный баланс {0}, пополнение {1}, итог {2}")
        @CsvSource({
                "1000, 500, 1500",
                "500, 250, 750",
                "0, 100, 100"
        })
        @Severity(SeverityLevel.CRITICAL)
        void testDepositTopUp(String initial, String add, String expected) {
            var deposit = new Deposit("Standard", Currency.getInstance("EUR"), new BigDecimal(initial));
            deposit.deposit(new BigDecimal(add));
            assertEquals(new BigDecimal(expected), deposit.getBalance());
        }

        @Test
        @DisplayName("4.2 Закрытие вклада")
        @Severity(SeverityLevel.NORMAL)
        void testDepositClose() {
            var deposit = new Deposit("Standard", Currency.getInstance("EUR"), BigDecimal.valueOf(1000));
            deposit.close();
            assertTrue(deposit.isClosed());
        }

        @Test
        @DisplayName("4.3 Пополнение закрытого вклада — ошибка")
        @Severity(SeverityLevel.CRITICAL)
        void testDepositAfterCloseFails() {
            var deposit = new Deposit("Standard", Currency.getInstance("EUR"), BigDecimal.valueOf(1000));
            deposit.close();
            assertThrows(IllegalStateException.class, () -> deposit.deposit(BigDecimal.valueOf(100)));
        }
    }

    @Nested
    @DisplayName("5. Абстрактная карта (базовый класс)")
    class AbstractCardTests {

        static class TestCard extends Card {
            public TestCard(String name, Currency currency, BigDecimal balance) {
                super(name, currency, balance);
            }
        }

        @Test
        @DisplayName("5.1 Общая логика пополнения и списания")
        @Severity(SeverityLevel.NORMAL)
        void testGenericCardBehavior() {
            var card = new TestCard("Test Card", Currency.getInstance("USD"), BigDecimal.valueOf(100));
            card.deposit(BigDecimal.valueOf(100));
            card.withdraw(BigDecimal.valueOf(50));
            assertEquals(BigDecimal.valueOf(150), card.getBalance());
        }
    }
}
