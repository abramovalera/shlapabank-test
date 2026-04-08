package ru.shlapabank.api.steps;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.shlapabank.api.ApiClient;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.generation.UserCredentials;
import ru.shlapabank.api.models.request.LoginRequest;
import ru.shlapabank.api.models.request.RegisterRequest;
import ru.shlapabank.enums.Endpoint;


public class AuthApiSteps {
    private final ApiClient apiClient = new ApiClient();

    /**
     * Генерирует данные нового пользователя.
     *
     * @return логин и пароль пользователя
     */
    @Step("Сгенерированы данные пользователя")
    public UserCredentials generateUserCredentials() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();
        return new UserCredentials(login, password);
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return HTTP-ответ регистрации
     */
    @Step("Регистрация пользователя")
    public Response register(String login, String password) {
        return registerWithoutStep(login, password);
    }

    /**
     * Авторизует пользователя.
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return HTTP-ответ авторизации
     */
    @Step("Авторизация пользователя")
    public Response login(String login, String password) {
        return loginWithoutStep(login, password);
    }

    /**
     * Регистрирует пользователя и выполняет авторизацию (один шаг Allure с двумя HTTP-вызовами).
     *
     * @param login    логин пользователя
     * @param password пароль пользователя
     * @return HTTP-ответ авторизации
     */
    @Step("Регистрация и авторизация пользователя")
    public Response registerAndLogin(String login, String password) {
        registerWithoutStep(login, password);
        return loginWithoutStep(login, password);
    }

    private Response registerWithoutStep(String login, String password) {
        return apiClient.post(Endpoint.REGISTER.path(), new RegisterRequest(login, password));
    }

    private Response loginWithoutStep(String login, String password) {
        return apiClient.post(Endpoint.LOGIN.path(), new LoginRequest(login, password));
    }
}
