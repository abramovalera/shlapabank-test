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
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("UI")
@Feature("Счета")
@DisplayName("Счета (UI)")
class AccountUiTest extends UiAuthenticatedBaseTest {

    private final DashboardPage dashboard = new DashboardPage();
    private final AccountSteps accountSteps = new AccountSteps();

    @Tag("smoke")
    @Test
    @DisplayName("Открытие счёта (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void openAccountTest() {
        dashboard.shouldBeLoaded()
                .openNewAccount("DEBIT", "RUB");

        AccountResponse viaApi = accountSteps.getAccounts(token).stream()
                .filter(a -> a.getAccountType() == AccountType.DEBIT && a.getCurrency() == Currency.RUB)
                .max(Comparator.comparing(AccountResponse::getId))
                .orElseThrow(() -> new AssertionError("Нет дебетового RUB счёта после открытия в UI"));

        dashboard.shouldShowAccountRow(viaApi.getId())
                .accountRowShouldContain(viaApi.getId(), "Дебетовый")
                .accountRowShouldContain(viaApi.getId(), "RUB");
        assertThat(viaApi.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Tag("smoke")
    @Test
    @DisplayName("Пополнение счёта отражается в списке счетов (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void balanceUpdatedAfterTopupTest() {
        dashboard.shouldBeLoaded();
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);
        BigDecimal amount = new BigDecimal("1000.00");
        accountSteps.addBalanceWithOtp(token, account.getId(), amount);

        dashboard.refresh()
                .shouldShowAccountRow(account.getId());

        AccountResponse updated = accountSteps.getAccounts(token).stream()
                .filter(a -> a.getId().equals(account.getId()))
                .findFirst()
                .orElseThrow();
        assertThat(updated.getBalance()).isEqualByComparingTo(amount);
    }

    @Tag("smoke")
    @Test
    @DisplayName("Закрытие счёта (POZITIVE)")
    @Severity(SeverityLevel.CRITICAL)
    void closeAccountTest() {
        dashboard.shouldBeLoaded();
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);

        dashboard.refresh()
                .closeAccountById(account.getId());

        boolean stillThere = accountSteps.getAccounts(token).stream()
                .anyMatch(a -> a.getId().equals(account.getId()));
        assertThat(stillThere).isFalse();
    }

    @Tag("smoke")
    @Test
    @DisplayName("Установка приоритетного счёта (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void setPrimaryAccountTest() {
        dashboard.shouldBeLoaded()
                .openNewAccount("DEBIT", "RUB")
                .savePrimaryAccounts();

        AccountResponse primary = accountSteps.getAccounts(token).stream()
                .filter(a -> a.getAccountType() == AccountType.DEBIT && a.getCurrency() == Currency.RUB)
                .filter(a -> Boolean.TRUE.equals(a.getIsPrimary()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Нет приоритетного дебетового RUB счёта"));
        assertThat(primary.getIsPrimary()).isTrue();
    }
}
