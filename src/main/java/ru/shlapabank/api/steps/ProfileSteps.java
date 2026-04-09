package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import ru.shlapabank.api.models.request.ProfileUpdateRequest;
import ru.shlapabank.api.models.response.ProfileResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.Endpoint;

public class ProfileSteps {

    @Step("Получение профиля")
    public ProfileResponse getProfile(String token) {
        return ApiRequestSpec.auth(token)
                .get(Endpoint.PROFILE.path())
                .then()
                .statusCode(200)
                .extract()
                .as(ProfileResponse.class);
    }

    @Step("Обновление профиля")
    public ProfileResponse updateProfile(String token, ProfileUpdateRequest request) {
        return ApiRequestSpec.auth(token)
                .body(request)
                .put(Endpoint.PROFILE.path())
                .then()
                .statusCode(200)
                .extract()
                .as(ProfileResponse.class);
    }
}
