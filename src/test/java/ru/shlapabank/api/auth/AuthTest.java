package ru.shlapabank.api.auth;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.UserCredentials;
import ru.shlapabank.api.models.response.TokenResponse;
import ru.shlapabank.enums.UserRole;
import ru.shlapabank.api.steps.AuthApiSteps;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Авторизация")
public class AuthTest {

    private final AuthApiSteps authApiSteps = new AuthApiSteps();

    @Test
    @DisplayName("Авторизация пользователя")
    void loginUserTest() {
        UserCredentials user = authApiSteps.generateUserCredentials();

        authApiSteps.register(user);
        Response rawResponse = authApiSteps.login(user);
        TokenResponse response = rawResponse.as(TokenResponse.class);

        assertThat(rawResponse.statusCode()).isEqualTo(200);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getAccessToken()).isNotBlank();
            softly.assertThat(response.getTokenType()).isEqualTo("bearer");
            softly.assertThat(response.getRole()).isEqualTo(UserRole.CLIENT);
        });
    }
}
