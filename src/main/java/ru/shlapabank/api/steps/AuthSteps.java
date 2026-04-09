package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import ru.shlapabank.api.models.request.LoginRequest;
import ru.shlapabank.api.models.request.RegisterRequest;
import ru.shlapabank.api.models.response.RegisterResponse;
import ru.shlapabank.api.models.response.TokenResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.Endpoint;

public class AuthSteps {

    @Step("Регистрация пользователя '{login}'")
    public RegisterResponse register(String login, String password) {
        return ApiRequestSpec.base()
                .body(new RegisterRequest(login, password))
                .post(Endpoint.REGISTER.path())
                .then()
                .statusCode(201)
                .extract()
                .as(RegisterResponse.class);
    }


    @Step("Авторизация пользователя '{login}'")
    public TokenResponse login(String login, String password) {
        return ApiRequestSpec.base()
                .body(new LoginRequest(login, password))
                .post(Endpoint.LOGIN.path())
                .then()
                .statusCode(200)
                .extract()
                .as(TokenResponse.class);
    }
}