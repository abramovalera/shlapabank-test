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
import ru.shlapabank.api.steps.TransactionSteps;
import ru.shlapabank.api.steps.TransferSteps;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.shlapabank.api.check.Checks.assertCompletedTransaction;
import static ru.shlapabank.api.check.Checks.assertZeroFee;

@Tag("API")
@Epic("API")
@Feature("Транзакции")
@DisplayName("История транзакций")
class TransactionTest extends AuthenticatedBaseTest {

    private final AccountSteps accountSteps = new AccountSteps();
    private final TransactionSteps transactionSteps = new TransactionSteps();
    private final TransferSteps transferSteps = new TransferSteps();

    @Tag("smoke")
    @Test
    @DisplayName("GET Топап появляется в истории с корректными полями (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void topupAppearsInHistoryTest() {
        BigDecimal amount = new BigDecimal("1000.00");
        AccountResponse account = accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, amount);

        TransactionResponse topup = transactionSteps.getTransactions(token).stream()
                .filter(t -> "TOPUP".equals(t.getType()) && account.getId().equals(t.getToAccountId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Транзакция TOPUP не найдена в истории"));

        assertCompletedTransaction(topup, "TOPUP");
        assertZeroFee(topup);
        assertThat(new BigDecimal(topup.getMoney().getAmount())).as("amount").isEqualByComparingTo(amount);
        assertThat(new BigDecimal(topup.getMoney().getTotal())).as("total = amount (fee=0)").isEqualByComparingTo(amount);
        assertThat(topup.getMoney().getCurrency()).as("валюта").isEqualTo("RUB");
        assertThat(topup.getToAccountId()).as("to_account_id").isEqualTo(account.getId());
    }

    @Tag("smoke")
    @Test
    @DisplayName("GET Перевод появляется в истории с корректными счетами (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void transferAppearsInHistoryTest() {
        BigDecimal transferAmount = new BigDecimal("300.00");
        AccountResponse from = accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, new BigDecimal("1000.00"));
        AccountResponse to = accountSteps.openAccount(token, AccountType.SAVINGS, Currency.RUB);

        transferSteps.transfer(token, from.getId(), to.getId(), transferAmount);

        TransactionResponse transfer = transactionSteps.getTransactions(token).stream()
                .filter(t -> "TRANSFER".equals(t.getType()) && from.getId().equals(t.getFromAccountId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Транзакция TRANSFER не найдена в истории"));

        assertCompletedTransaction(transfer, "TRANSFER", "P2P_TRANSFER", "p2p_transfer");
        assertThat(transfer.getFromAccountId()).as("from_account_id").isEqualTo(from.getId());
        assertThat(transfer.getToAccountId()).as("to_account_id").isEqualTo(to.getId());
        assertThat(new BigDecimal(transfer.getMoney().getAmount())).as("сумма перевода").isEqualByComparingTo(transferAmount);
        assertZeroFee(transfer);
    }
}
