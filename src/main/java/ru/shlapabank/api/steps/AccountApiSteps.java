package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.shlapabank.api.ApiClient;
import ru.shlapabank.api.models.request.AccountCreateRequest;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.enums.Endpoint;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AccountApiSteps {

    private final ApiClient apiClient = new ApiClient();

    private Response openAccountRaw(AccountCreateRequest request, String accessToken) {
        return apiClient.post(Endpoint.ACCOUNTS.path(), request, accessToken);
    }

    private Response getAccountsRaw(String accessToken) {
        return apiClient.get(Endpoint.ACCOUNTS.path(), accessToken);
    }

    private Response topUpAccountRaw(Long accountId, BigDecimal amount, String otpCode, String accessToken) {
        Map<String, Object> body = new HashMap<>();
        body.put("amount", amount);
        body.put("otp_code", otpCode);
        body.put("purpose", "salary");
        return apiClient.post(Endpoint.ACCOUNTS.path() + "/" + accountId + "/topup", body, accessToken);
    }

    @Step("Открытие счёта: тип {request.account_type}, валюта {request.currency}")
    public Response openAccount(AccountCreateRequest request, String accessToken) {
        return openAccountRaw(request, accessToken);
    }

    @Step("Получение списка счетов пользователя")
    public Response getAccounts(String accessToken) {
        return getAccountsRaw(accessToken);
    }

    @Step("Получение счета по id={accountId}")
    public AccountResponse getAccountById(Long accountId, String accessToken) {
        AccountResponse[] accounts = getAccountsRaw(accessToken).as(AccountResponse[].class);
        return Arrays.stream(accounts)
                .filter(account -> account.getId().equals(accountId))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Не найден счет id=" + accountId));
    }

    @Step("Пополнение счёта id={accountId} на сумму {amount}")
    public Response topUpAccount(Long accountId, BigDecimal amount, String otpCode, String accessToken) {
        return topUpAccountRaw(accountId, amount, otpCode, accessToken);
    }
}
