package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import ru.shlapabank.api.ApiClient;
import ru.shlapabank.enums.Endpoint;

public class HelperApiSteps {

    private final ApiClient apiClient = new ApiClient();

    @Step("Получить OTP код")
    public String getOtpCode(String accessToken) {
        String otp = apiClient.get(Endpoint.OTP_PREVIEW.path(), accessToken)
                .jsonPath()
                .getString("otp");
        if (otp == null) {
            throw new AssertionError("отсутствует otp код");
        }
        return otp;
    }
}
