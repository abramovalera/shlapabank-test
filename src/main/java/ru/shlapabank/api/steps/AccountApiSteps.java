package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.shlapabank.api.ApiClient;
import ru.shlapabank.api.models.request.AccountCreateRequest;
import ru.shlapabank.enums.Endpoint;

public class AccountApiSteps {

    private final ApiClient apiClient = new ApiClient();

    /**
     * Открывает новый счет пользователя.
     *
     * @param request     тело запроса на открытие счета
     * @param accessToken bearer токен пользователя
     * @return HTTP-ответ открытия счета
     */
    @Step("Открытие счёта: тип {request.account_type}, валюта {request.currency}")
    public Response openAccount(AccountCreateRequest request, String accessToken) {
        return apiClient.post(Endpoint.ACCOUNTS.path(), request, accessToken);
    }
}
