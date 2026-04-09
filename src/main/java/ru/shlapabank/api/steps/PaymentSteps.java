package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import ru.shlapabank.api.models.response.OperatorResponse;
import ru.shlapabank.api.models.response.VendorResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.Endpoint;

import java.util.List;

public class PaymentSteps {

    @Step("Получение списка мобильных операторов")
    public List<OperatorResponse> getMobileOperators(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.PAYMENTS_MOBILE_OPERATORS.path())
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<OperatorResponse>>() {});
    }

    @Step("Получение списка поставщиков")
    public List<VendorResponse> getVendorProviders(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.PAYMENTS_VENDOR_PROVIDERS.path())
                .then()
                .statusCode(200)
                .extract()
                .as(new TypeRef<List<VendorResponse>>() {});
    }
}
