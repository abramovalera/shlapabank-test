package ru.shlapabank.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.models.response.RegisterResponse;
import ru.shlapabank.api.models.response.TokenResponse;
import ru.shlapabank.api.steps.AuthSteps;
import ru.shlapabank.enums.UserRole;
import ru.shlapabank.enums.UserStatus;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("API")
@Feature("Авторизация и регистрация")
@DisplayName("Регистрация и авторизация")
public class AuthTest extends BaseTest {

    private final AuthSteps authSteps = new AuthSteps();

    @Tag("smoke")
    @Test
    @DisplayName("POST Регистрация пользователя (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void registerUserTest() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();

        RegisterResponse response = authSteps.register(login, password);

        assertThat(response.getId()).as("id пользователя").isPositive();
        assertThat(response.getLogin()).as("login").isEqualTo(login);
        assertThat(response.getRole()).as("role").isEqualTo(UserRole.CLIENT);
        assertThat(response.getStatus()).as("status").isEqualTo(UserStatus.ACTIVE);
    }

    @Tag("smoke")
    @Test
    @DisplayName("POST Авторизация пользователя (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void loginUserTest() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();

        authSteps.register(login, password);
        TokenResponse token = authSteps.login(login, password);

        assertThat(token.getAccessToken()).as("access_token").isNotBlank();
        assertThat(token.getTokenType()).as("token_type").isEqualToIgnoringCase("bearer");
        assertThat(token.getRole()).as("role").isEqualTo(UserRole.CLIENT);
    }
}
