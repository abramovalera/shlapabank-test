package ru.shlapabank.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class DashboardPage {

    @Step("Ждём загрузку дашборда")
    public DashboardPage shouldBeLoaded() {
        $("[data-testid='balance-totals']").shouldBe(visible, Duration.ofSeconds(10));
        return this;
    }

    @Step("Обновляем страницу дашборда")
    public DashboardPage refresh() {
        Selenide.refresh();
        return shouldBeLoaded();
    }

    @Step("Вкладка «Главная»")
    public DashboardPage openHomeTab() {
        $("[data-page-target='home']").click();
        return this;
    }

    @Step("Вкладка «Платежи»")
    public DashboardPage openPaymentsTab() {
        $("[data-page-target='payments']").click();
        return this;
    }

    @Step("Вкладка «Ещё» ")
    public DashboardPage openMoreTab() {
        $("[data-page-target='more']").click();
        return this;
    }

    @Step("Открываем счёт: тип={accountType}, валюта={currency}")
    public DashboardPage openNewAccount(String accountType, String currency) {
        openHomeTab();
        $("[data-testid='btn-open-account']").shouldBe(visible).click();
        $("#openAccountModal").shouldBe(visible);
        $("#openAccountType").selectOptionByValue(accountType);
        $("#openAccountCurrency").selectOptionByValue(currency);
        $("#openAccountOk").click();
        $("[data-testid='toast']").shouldHave(text("Открыт"), Duration.ofSeconds(15));
        return shouldBeLoaded();
    }

    @Step("Счёт с id={accountId} отображается в списке")
    public DashboardPage shouldShowAccountRow(long accountId) {
        accountRow(accountId).shouldBe(visible);
        return this;
    }

    @Step("В строке счёта id={accountId} есть текст «{fragment}»")
    public DashboardPage accountRowShouldContain(long accountId, String fragment) {
        accountRow(accountId).shouldHave(text(fragment));
        return this;
    }

    @Step("Активных счетов нет")
    public DashboardPage shouldShowEmptyAccountsMessage() {
        $("[data-testid='empty-accounts']").shouldBe(visible);
        return this;
    }

    @Step("Закрываем счёт id={accountId} ")
    public DashboardPage closeAccountById(long accountId) {
        openHomeTab();
        SelenideElement row = accountRow(accountId);
        row.$("[data-testid='btn-account-close']").click();
        $("#confirmCloseOk").shouldBe(visible).click();
        $("[data-testid='toast']").shouldHave(text("Закрыт"), Duration.ofSeconds(15));
        row.shouldNot(exist, Duration.ofSeconds(10));
        return this;
    }

    @Step("Cheat: обнулить баланс счёта id={accountId}")
    public DashboardPage cheatZeroBalance(long accountId) {
        openHomeTab();
        $("[data-testid='btn-hat-cheat']").click();
        $("#cheatModal").shouldBe(visible);
        $("#cheatAccountSelect").selectOptionByValue(String.valueOf(accountId));
        $("#cheatAction").selectOptionByValue("zero");
        $("#cheatApply").click();
        $("[data-testid='toast']").shouldHave(text("обновлён"), Duration.ofSeconds(15));
        return shouldBeLoaded();
    }

    @Step("Перевод между своими счетами: с {fromAccountId} на {toAccountId}, сумма {amount}")
    public DashboardPage transferBetweenOwnAccounts(long fromAccountId, long toAccountId, String amount) {
        openHomeTab();
        $("[data-testid='btn-transfer-own']").click();
        $("[data-testid='modal-transfer-own']").shouldBe(visible);
        $("#homeTransferFrom").selectOptionByValue(String.valueOf(fromAccountId));
        $("#homeTransferTo").selectOptionByValue(String.valueOf(toAccountId));
        $("#homeTransferAmount").setValue(amount);
        $("[data-testid='modal-transfer-own-submit']").click();
        $("[data-testid='toast']").shouldHave(text("Перевод"), Duration.ofSeconds(20));
        return shouldBeLoaded();
    }

    @Step("Проверяем блок курсов валют на главной")
    public DashboardPage shouldShowExchangeRatesForMajorCurrencies() {
        openHomeTab();
        SelenideElement box = $("#exchangeRatesBox");
        box.shouldBe(visible);
        box.shouldHave(text("USD"));
        box.shouldHave(text("EUR"));
        box.shouldHave(text("CNY"));
        return this;
    }

    @Step("Открываем модалку «По номеру счёта» и проверяем подсказку дневного лимита")
    public DashboardPage shouldShowDailyLimitHintAfterSelectingAccount() {
        openHomeTab();
        $("[data-testid='btn-transfer-by-account']").click();
        $("[data-testid='modal-by-account']").shouldBe(visible);
        SelenideElement fromSel = $("#homeByAccountFrom");
        ElementsCollection opts = fromSel.$$("option");
        String chosen = null;
        for (int i = 0; i < opts.size(); i++) {
            String v = opts.get(i).getAttribute("value");
            if (v != null && !v.isBlank()) {
                chosen = v;
                break;
            }
        }
        if (chosen == null) {
            throw new AssertionError("Нет счёта для выбора в модалке перевода");
        }
        fromSel.selectOptionByValue(chosen);
        $("#homeByAccountLimitLabel").shouldNotHave(text("Выберите счёт"));
        $("#homeByAccountLimitLabel").shouldHave(text("лимит"));
        $("[data-testid='modal-by-account-cancel']").click();
        return this;
    }

    @Step("Категория платежей: {category}")
    public DashboardPage selectPaymentCategory(String dataPayTarget) {
        $("button.payments-category-btn[data-pay-target='" + dataPayTarget + "']").click();
        return this;
    }

    @Step("Список мобильных операторов")
    public DashboardPage shouldLoadMobileOperators() {
        openPaymentsTab();
        selectPaymentCategory("mobile");
        $("#mobileAccount").shouldBe(visible);
        $("#mobileOperatorSbBtn").shouldBe(visible).click();
        ElementsCollection opts = $$("#mobileOperatorListbox [role='option']");
        if (opts.isEmpty()) {
            opts = $$("#mobileOperatorListbox li");
        }
        opts.shouldHave(sizeGreaterThan(0), Duration.ofSeconds(15));
        $("#mobileOperatorSbBtn").click();
        return this;
    }

    @Step("Список поставщиков")
    public DashboardPage shouldLoadVendorProviders() {
        openPaymentsTab();
        selectPaymentCategory("internet");
        $("#vendorForm").shouldBe(visible, Duration.ofSeconds(5));
        $("#vendorProvider").$$("option").shouldHave(sizeGreaterThan(1), Duration.ofSeconds(15));
        return this;
    }

    @Step("Профиль: имя={firstName}, email={email}, сохранить")
    public DashboardPage updateProfile(String firstName, String email) {
        openMoreTab();
        $("#profileForm").shouldBe(visible);
        $("#firstName").setValue(firstName);
        $("#email").setValue(email);
        $("#profileForm").$("button[type='submit']").click();
        $("#firstName").shouldHave(value(firstName), Duration.ofSeconds(10));
        return this;
    }

    @Step("Приоритетные счета: сохранить текущий выбор")
    public DashboardPage savePrimaryAccounts() {
        openMoreTab();
        $("#primaryAccountsBtn").shouldBe(visible).click();
        $("#primaryAccountsModal").shouldBe(visible);
        $$("#primaryAccountsFields select").shouldHave(sizeGreaterThan(0));
        $("#primaryAccountsForm").$("button[type='submit']").click();
        $("[data-testid='toast']").shouldHave(text("сохранены"), Duration.ofSeconds(15));
        return shouldBeLoaded();
    }

    @Step("В списке последних операций есть запись с текстом «{snippet}»")
    public DashboardPage recentTransactionsShouldContain(String snippet) {
        openHomeTab();
        $("#recentTransactions").shouldHave(text(snippet), Duration.ofSeconds(15));
        return this;
    }

    private static SelenideElement accountRow(long accountId) {
        return $(".account-item[data-account-id='" + accountId + "']");
    }
}
