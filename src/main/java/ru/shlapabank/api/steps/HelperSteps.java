package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import ru.shlapabank.api.models.response.OtpPreviewResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.Endpoint;

public class HelperSteps {

    @Step("Получение OTP кода")
    public String getOtp(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.OTP_PREVIEW.path())
                .then()
                .statusCode(200)
                .extract()
                .as(OtpPreviewResponse.class)
                .getOtp();
    }
}