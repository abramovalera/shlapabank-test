package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import ru.shlapabank.api.models.request.AccountCreateRequest;
import ru.shlapabank.api.models.request.TopUpRequest;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;
import ru.shlapabank.enums.Endpoint;

import java.math.BigDecimal;
import java.util.List;

public class AccountSteps {

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

    @Step("Получение списка счетов")
    public List<AccountResponse> getAccounts(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.ACCOUNTS.path())
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<AccountResponse>>() {
                });
    }

    @Step("Пополнение счета {accountId} на сумму {amount}")
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
}