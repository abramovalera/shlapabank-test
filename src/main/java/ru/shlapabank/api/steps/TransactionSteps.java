package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import ru.shlapabank.api.models.response.TransactionResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.Endpoint;

import java.util.List;

public class TransactionSteps {

    @Step("Получение истории транзакций")
    public List<TransactionResponse> getTransactions(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.TRANSACTIONS.path())
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<TransactionResponse>>() {});
    }
}
