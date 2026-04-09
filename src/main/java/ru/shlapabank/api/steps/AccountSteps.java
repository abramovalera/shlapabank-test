package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import ru.shlapabank.api.models.request.AccountCreateRequest;
import ru.shlapabank.api.models.request.PrimaryAccountsRequest;
import ru.shlapabank.api.models.request.TopUpRequest;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.api.models.response.ActionResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;
import ru.shlapabank.enums.Endpoint;

import java.math.BigDecimal;
import java.util.List;

public class AccountSteps {

    private final HelperSteps helperSteps = new HelperSteps();

    @Step("Открытие счета: тип={accountType}, валюта={currency}")
    public AccountResponse openAccount(String token, AccountType accountType, Currency currency) {
        return ApiRequestSpec.auth(token)
                .body(new AccountCreateRequest(accountType, currency))
                .post(Endpoint.ACCOUNTS.path())
                .then()
                .statusCode(201)
                .extract()
                .as(AccountResponse.class);
    }

    /**
     * Открывает счёт и сразу пополняет его — типовой паттерн подготовки данных.
     * OTP берётся автоматически.
     */
    @Step("Открытие и пополнение счета: тип={accountType}, валюта={currency}, сумма={initialBalance}")
    public AccountResponse openFundedAccount(String token, AccountType accountType, Currency currency, BigDecimal initialBalance) {
        AccountResponse account = openAccount(token, accountType, currency);
        addBalanceWithOtp(token, account.getId(), initialBalance);
        return account;
    }

    @Step("Получение списка счетов")
    public List<AccountResponse> getAccounts(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.ACCOUNTS.path())
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<AccountResponse>>() {});
    }

    /**
     * Пополняет счёт: автоматически получает OTP и выполняет топап.
     */
    @Step("Пополнение счета #{accountId} на сумму {amount} (с автоматическим OTP)")
    public Response addBalanceWithOtp(String token, Long accountId, BigDecimal amount) {
        String otp = helperSteps.getOtp(token);
        return addBalance(token, accountId, amount, otp);
    }

    @Step("Пополнение счета #{accountId} на сумму {amount}")
    public Response addBalance(String token, Long accountId, BigDecimal amount, String otp) {
        return ApiRequestSpec.auth(token)
                .pathParam("account_id", accountId)
                .body(new TopUpRequest(amount, otp))
                .post(Endpoint.ACCOUNT_TOPUP.path())
                .then()
                .statusCode(201)
                .extract()
                .response();
    }

    @Step("Попытка пополнения счета #{accountId} на сумму {amount}")
    public Response tryAddBalance(String token, Long accountId, BigDecimal amount, String otp) {
        return ApiRequestSpec.auth(token)
                .pathParam("account_id", accountId)
                .body(new TopUpRequest(amount, otp))
                .post(Endpoint.ACCOUNT_TOPUP.path())
                .then()
                .extract()
                .response();
    }

    /**
     * Пробует пополнить счёт, автоматически получая OTP.
     * Не проверяет статус ответа — для негативных сценариев.
     */
    @Step("Попытка пополнения счета #{accountId} на сумму {amount} (с автоматическим OTP)")
    public Response tryAddBalanceWithOtp(String token, Long accountId, BigDecimal amount) {
        String otp = helperSteps.getOtp(token);
        return tryAddBalance(token, accountId, amount, otp);
    }

    @Step("Закрытие счета #{accountId}")
    public ActionResponse closeAccount(String token, Long accountId) {
        return ApiRequestSpec.auth(token)
                .pathParam("account_id", accountId)
                .delete(Endpoint.ACCOUNT_CLOSE.path())
                .then()
                .statusCode(200)
                .extract()
                .as(ActionResponse.class);
    }

    @Step("Установка приоритетных счетов")
    public ActionResponse setPrimaryAccounts(String token, List<Long> accountIds) {
        return ApiRequestSpec.auth(token)
                .body(new PrimaryAccountsRequest(accountIds))
                .put(Endpoint.ACCOUNTS_PRIMARY.path())
                .then()
                .statusCode(200)
                .extract()
                .as(ActionResponse.class);
    }
}
