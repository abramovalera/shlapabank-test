package ru.shlapabank.api.auth;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.UserCredentials;
import ru.shlapabank.api.models.response.TokenResponse;
import ru.shlapabank.enums.UserRole;
import ru.shlapabank.api.steps.AuthApiSteps;

@DisplayName("Авторизация")
public class AuthTest {

    private final AuthApiSteps authApiSteps = new AuthApiSteps();

    @Test
    @DisplayName("Авторизация пользователя")
    void loginUser() {
        UserCredentials user = authApiSteps.generateUserCredentials();

        authApiSteps.register(user.getLogin(), user.getPassword());
        Response rawResponse = authApiSteps.login(user.getLogin(), user.getPassword());
        TokenResponse response = rawResponse.as(TokenResponse.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rawResponse.statusCode()).isEqualTo(200);
            softly.assertThat(response.getAccess_token()).isNotBlank();
            softly.assertThat(response.getToken_type()).isEqualTo("bearer");
            softly.assertThat(response.getRole()).isEqualTo(UserRole.CLIENT);
        });
    }
}
