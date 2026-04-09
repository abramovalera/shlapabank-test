package ru.shlapabank.api.steps;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.shlapabank.api.ApiClient;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.generation.UserCredentials;
import ru.shlapabank.api.models.request.LoginRequest;
import ru.shlapabank.api.models.request.RegisterRequest;
import ru.shlapabank.api.models.response.TokenResponse;
import ru.shlapabank.enums.Endpoint;


public class AuthApiSteps {
    private final ApiClient apiClient = new ApiClient();

    private Response registerRaw(UserCredentials user) {
        return apiClient.post(Endpoint.REGISTER.path(), new RegisterRequest(user.getLogin(), user.getPassword()));
    }

    private Response loginRaw(UserCredentials user) {
        return apiClient.post(Endpoint.LOGIN.path(), new LoginRequest(user.getLogin(), user.getPassword()));
    }

    @Step("Сгенерированы данные пользователя")
    public UserCredentials generateUserCredentials() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();
        return new UserCredentials(login, password);
    }

    @Step("Регистрация пользователя")
    public Response register(UserCredentials user) {
        return registerRaw(user);
    }

    @Step("Авторизация пользователя")
    public Response login(UserCredentials user) {
        return loginRaw(user);
    }

    @Step("Авторизация и получение access token")
    public String loginAndGetToken(UserCredentials user) {
        TokenResponse tokenResponse = loginRaw(user).as(TokenResponse.class);
        return tokenResponse.getAccessToken();
    }
}
