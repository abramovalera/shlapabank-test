package ru.shlapabank.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.api.models.response.DailyUsageResponse;
import ru.shlapabank.api.models.response.TransactionResponse;
import ru.shlapabank.api.models.response.TransferRatesResponse;
import ru.shlapabank.api.steps.AccountSteps;
import ru.shlapabank.api.steps.TransferSteps;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shlapabank.api.check.Checks.assertCompletedTransaction;
import static ru.shlapabank.api.check.Checks.assertZeroFee;

@Tag("API")
@Epic("API")
@Feature("Переводы")
@DisplayName("Переводы")
class TransferTest extends AuthenticatedBaseTest {

    private final AccountSteps accountSteps = new AccountSteps();
    private final TransferSteps transferSteps = new TransferSteps();

    @Tag("smoke")
    @Test
    @DisplayName("POST Перевод между своими счетами (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void transferBetweenOwnAccountsTest() {
        BigDecimal topUpAmount = new BigDecimal("1000.00");
        BigDecimal transferAmount = new BigDecimal("500.00");

        AccountResponse from = accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, topUpAmount);
        AccountResponse to = accountSteps.openAccount(token, AccountType.SAVINGS, Currency.RUB);

        TransactionResponse tx = transferSteps.transfer(token, from.getId(), to.getId(), transferAmount);

        assertCompletedTransaction(tx, "TRANSFER", "P2P_TRANSFER", "p2p_transfer");
        assertZeroFee(tx);
        assertThat(tx.getFromAccountId()).as("счет списания").isEqualTo(from.getId());
        assertThat(tx.getToAccountId()).as("счет зачисления").isEqualTo(to.getId());
        assertThat(new BigDecimal(tx.getMoney().getAmount())).as("сумма").isEqualByComparingTo(transferAmount);
        assertThat(tx.getMoney().getCurrency()).as("валюта").isEqualTo("RUB");

        List<AccountResponse> accounts = accountSteps.getAccounts(token);
        AccountResponse updatedFrom = accounts.stream().filter(a -> a.getId().equals(from.getId())).findFirst()
                .orElseThrow(() -> new AssertionError("Счет-источник не найден"));
        AccountResponse updatedTo = accounts.stream().filter(a -> a.getId().equals(to.getId())).findFirst()
                .orElseThrow(() -> new AssertionError("Счет-получатель не найден"));

        assertThat(updatedFrom.getBalance()).as("баланс источника").isEqualByComparingTo(topUpAmount.subtract(transferAmount));
        assertThat(updatedTo.getBalance()).as("баланс получателя").isEqualByComparingTo(transferAmount);
    }

    @Tag("smoke")
    @Test
    @DisplayName("GET Курсы валют (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void getRatesTest() {
        TransferRatesResponse rates = transferSteps.getRates(token);

        assertThat(rates.getRates()).as("курсы валют").containsKeys("RUB", "USD", "EUR", "CNY");
        assertThat(rates.getRates().values()).as("все курсы положительны")
                .allSatisfy(s -> assertThat(new BigDecimal(s)).isPositive());
    }

    @Tag("smoke")
    @Test
    @DisplayName("GET Суточный лимит (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void getDailyUsageTest() {
        DailyUsageResponse usage = transferSteps.getDailyUsage(token);
        Map<String, BigDecimal> remaining = usage.remainingByCurrency();

        assertThat(remaining).as("лимиты по валютам").containsKeys("RUB", "USD", "EUR", "CNY");
        assertThat(remaining.values()).as("остаток лимита >= 0")
                .allSatisfy(value -> assertThat(value).isGreaterThanOrEqualTo(BigDecimal.ZERO));
    }
}
