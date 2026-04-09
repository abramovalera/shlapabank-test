package ru.shlapabank.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.steps.AccountSteps;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;
import ru.shlapabank.ui.pages.DashboardPage;

import java.math.BigDecimal;

@Tag("ui")
@Epic("UI")
@Feature("Платежи")
@DisplayName("Платежи")
class PaymentUiTest extends UiAuthenticatedBaseTest {

    private final DashboardPage dashboard = new DashboardPage();
    private final AccountSteps accountSteps = new AccountSteps();

    @Tag("smoke")
    @Test
    @DisplayName("Список мобильных операторов (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void getMobileOperatorsTest() {
        dashboard.shouldBeLoaded();
        accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, new BigDecimal("500.00"));
        dashboard.refresh()
                .shouldLoadMobileOperators();
    }

    @Tag("smoke")
    @Test
    @DisplayName("Список поставщиков услуг (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void getVendorProvidersTest() {
        dashboard.shouldBeLoaded();
        accountSteps.openFundedAccount(token, AccountType.DEBIT, Currency.RUB, new BigDecimal("500.00"));
        dashboard.refresh()
                .shouldLoadVendorProviders();
    }
}
