package ru.shlapabank.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.api.steps.AccountSteps;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;
import ru.shlapabank.ui.pages.DashboardPage;

import java.math.BigDecimal;

@Tag("ui")
@Epic("UI")
@Feature("Транзакции")
@DisplayName("История операций")
class TransactionUiTest extends UiAuthenticatedBaseTest {

    private final DashboardPage dashboard = new DashboardPage();
    private final AccountSteps accountSteps = new AccountSteps();

    @Tag("smoke")
    @Test
    @DisplayName("Пополнение отображается в последних операциях (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void topupAppearsInHistoryTest() {
        dashboard.shouldBeLoaded();
        BigDecimal amount = new BigDecimal("1000.00");
        accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, amount);

        dashboard.refresh()
                .recentTransactionsShouldContain("Пополнение");
    }

    @Tag("smoke")
    @Test
    @DisplayName("Перевод между своими счетами в истории операций(POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void transferAppearsInHistoryTest() {
        dashboard.shouldBeLoaded();
        BigDecimal transferAmount = new BigDecimal("300.00");
        AccountResponse from = accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, new BigDecimal("1000.00"));
        AccountResponse to = accountSteps.openAccount(token, AccountType.SAVINGS, Currency.RUB);

        dashboard.refresh()
                .transferBetweenOwnAccounts(from.getId(), to.getId(), transferAmount.toPlainString())
                .recentTransactionsShouldContain("Перевод");
    }
}
