package ru.shlapabank.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.api.models.response.TransactionResponse;
import ru.shlapabank.api.steps.AccountSteps;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shlapabank.api.check.Checks.assertCompletedTransaction;
import static ru.shlapabank.api.check.Checks.assertZeroFee;

@Epic("API")
@Feature("Счета")
@DisplayName("Счета")
class AccountTest extends AuthenticatedBaseTest {

    private final AccountSteps accountSteps = new AccountSteps();

    @Tag("smoke")
    @Test
    @DisplayName("POST Открытие счета (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void openAccountTest() {
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);

        assertThat(account.getId()).as("id счета").isPositive();
        assertThat(account.getAccountNumber()).as("номер счета").isNotBlank();
        assertThat(account.getAccountType()).as("тип счета").isEqualTo(AccountType.DEBIT);
        assertThat(account.getCurrency()).as("валюта").isEqualTo(Currency.RUB);
        assertThat(account.getBalance()).as("начальный баланс").isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Tag("smoke")
    @Test
    @DisplayName("POST Пополнение счета — транзакция корректна (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void addBalanceAccountTest() {
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);
        BigDecimal amount = new BigDecimal("1000.00");

        TransactionResponse tx = accountSteps.addBalanceWithOtp(token, account.getId(), amount)
                .as(TransactionResponse.class);

        assertCompletedTransaction(tx, "TOPUP");
        assertZeroFee(tx);
        assertThat(new BigDecimal(tx.getMoney().getAmount())).as("сумма пополнения").isEqualByComparingTo(amount);
    }

    @Tag("smoke")
    @Test
    @DisplayName("POST Баланс обновился после пополнения (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void balanceUpdatedAfterTopupTest() {
        BigDecimal amount = new BigDecimal("1000.00");
        AccountResponse account = accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, amount);

        AccountResponse updated = accountSteps.getAccounts(token).stream()
                .filter(a -> a.getId().equals(account.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Счет не найден после пополнения"));

        assertThat(updated.getBalance()).as("баланс после пополнения").isEqualByComparingTo(amount);
    }

    @Tag("smoke")
    @Test
    @DisplayName("DELETE Закрытие счета (POZITIVE)")
    @Severity(SeverityLevel.CRITICAL)
    void closeAccountTest() {
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);
        accountSteps.closeAccount(token, account.getId());

        boolean closedAccountPresent = accountSteps.getAccounts(token).stream()
                .anyMatch(a -> a.getId().equals(account.getId()));

        assertThat(closedAccountPresent)
                .as("закрытый счет не должен присутствовать в списке активных счетов")
                .isFalse();
    }

    @Tag("smoke")
    @Test
    @DisplayName("POST Закрытый счет нельзя пополнить (NEGATIVE)")
    @Severity(SeverityLevel.CRITICAL)
    void closedAccountCannotBeToppedUpTest() {
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);
        accountSteps.closeAccount(token, account.getId());

        var response = accountSteps.tryAddBalanceWithOtp(token, account.getId(), new BigDecimal("100.00"));

        assertThat(response.getStatusCode()).as("http-статус").isEqualTo(422);
        assertThat(response.jsonPath().getString("detail"))
                .as("код ошибки")
                .isEqualTo("account_already_closed");
    }

    @Tag("smoke")
    @Test
    @DisplayName("PUT Установка приоритетного счета (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void setPrimaryAccountTest() {
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);
        accountSteps.setPrimaryAccounts(token, List.of(account.getId()));

        AccountResponse primary = accountSteps.getAccounts(token).stream()
                .filter(a -> a.getId().equals(account.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Счет не найден после установки приоритета"));

        assertThat(primary.getIsPrimary()).as("счет помечен как приоритетный").isTrue();
    }
}
