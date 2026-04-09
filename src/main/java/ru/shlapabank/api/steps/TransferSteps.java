package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import ru.shlapabank.api.models.request.TransferRequest;
import ru.shlapabank.api.models.response.DailyUsageResponse;
import ru.shlapabank.api.models.response.TransactionResponse;
import ru.shlapabank.api.models.response.TransferRatesResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.Endpoint;

import java.math.BigDecimal;

public class TransferSteps {

    @Step("Перевод между своими счетами: {amount}")
    public TransactionResponse transfer(String token, Long fromAccountId, Long toAccountId, BigDecimal amount) {
        return ApiRequestSpec.auth(token)
                .body(new TransferRequest(fromAccountId, toAccountId, amount, null))
                .post(Endpoint.TRANSFERS.path())
                .then()
                .statusCode(201)
                .extract()
                .as(TransactionResponse.class);
    }

    @Step("Получение курсов валют")
    public TransferRatesResponse getRates(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.TRANSFERS_RATES.path())
                .then()
                .statusCode(200)
                .extract()
                .as(TransferRatesResponse.class);
    }

    @Step("Получение суточного лимита")
    public DailyUsageResponse getDailyUsage(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.TRANSFERS_DAILY_USAGE.path())
                .then()
                .statusCode(200)
                .extract()
                .as(DailyUsageResponse.class);
    }
}
