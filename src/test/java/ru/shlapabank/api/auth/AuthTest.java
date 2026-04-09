package ru.shlapabank.api.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.models.response.RegisterResponse;
import ru.shlapabank.api.models.response.TokenResponse;
import ru.shlapabank.api.steps.AuthSteps;
import ru.shlapabank.enums.UserRole;
import ru.shlapabank.enums.UserStatus;

import static ru.shlapabank.api.check.Checks.*;


@DisplayName("Авторизация/Регистрация")
public class AuthTest {

    private final AuthSteps authSteps = new AuthSteps();

    @Test
    @DisplayName("Регистрация пользователя")
    void registerUserTest() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();

        RegisterResponse response = authSteps.register(login, password);

        assertPositive(response.getId(), "id пользователя");
        assertEquals(response.getLogin(), login, "login");
        assertEquals(response.getRole(), UserRole.CLIENT, "role");
        assertEquals(response.getStatus(), UserStatus.ACTIVE, "status");
    }

    @Test
    @DisplayName("Авторизация пользователя")
    void loginUserTest() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();

        authSteps.register(login, password);
        TokenResponse token = authSteps.login(login, password);

        assertNotBlank(token.getAccessToken(), "access_token");
        assertEquals(token.getTokenType(), "bearer", "token_type");
        assertEquals(token.getRole(), UserRole.CLIENT, "role");
    }
}
