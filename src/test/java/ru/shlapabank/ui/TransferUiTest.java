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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("UI")
@Feature("Переводы")
@DisplayName("Переводы и лимиты (UI)")
class TransferUiTest extends UiAuthenticatedBaseTest {

    private final DashboardPage dashboard = new DashboardPage();
    private final AccountSteps accountSteps = new AccountSteps();

    @Tag("smoke")
    @Test
    @DisplayName("Перевод между своими счетами (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void transferBetweenOwnAccountsTest() {
        dashboard.shouldBeLoaded();
        BigDecimal topUpAmount = new BigDecimal("1000.00");
        BigDecimal transferAmount = new BigDecimal("500.00");

        AccountResponse from = accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, topUpAmount);
        AccountResponse to = accountSteps.openAccount(token, AccountType.SAVINGS, Currency.RUB);

        dashboard.refresh()
                .transferBetweenOwnAccounts(from.getId(), to.getId(), transferAmount.toPlainString())
                .accountRowShouldContain(from.getId(), "500")
                .accountRowShouldContain(to.getId(), "500");

        List<AccountResponse> accounts = accountSteps.getAccounts(token);
        AccountResponse updatedFrom = accounts.stream().filter(a -> a.getId().equals(from.getId())).findFirst().orElseThrow();
        AccountResponse updatedTo = accounts.stream().filter(a -> a.getId().equals(to.getId())).findFirst().orElseThrow();
        assertThat(updatedFrom.getBalance()).isEqualByComparingTo(topUpAmount.subtract(transferAmount));
        assertThat(updatedTo.getBalance()).isEqualByComparingTo(transferAmount);
    }

    @Tag("smoke")
    @Test
    @DisplayName("Курсы валют (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void getRatesTest() {
        dashboard.shouldBeLoaded()
                .shouldShowExchangeRatesForMajorCurrencies();
    }

    @Tag("smoke")
    @Test
    @DisplayName("Отображение дневного лимита в модалке перевода (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void getDailyUsageTest() {
        dashboard.shouldBeLoaded();
        accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, new BigDecimal("100.00"));
        dashboard.refresh()
                .shouldShowDailyLimitHintAfterSelectingAccount();
    }
}
