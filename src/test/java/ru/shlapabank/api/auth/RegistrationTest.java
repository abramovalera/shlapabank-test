package ru.shlapabank.api.auth;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.UserCredentials;
import ru.shlapabank.api.models.response.RegisterResponse;
import ru.shlapabank.api.steps.AuthApiSteps;
import ru.shlapabank.enums.UserRole;
import ru.shlapabank.enums.UserStatus;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Регистрация")
class RegistrationTest {
    private final AuthApiSteps authApiSteps = new AuthApiSteps();

    @Test
    @DisplayName("Регистрация пользователя")
    void registerNewUserTest() {
        UserCredentials user = authApiSteps.generateUserCredentials();
        Response rawResponse = authApiSteps.register(user);

        RegisterResponse response = rawResponse.as(RegisterResponse.class);
        assertThat(rawResponse.statusCode()).isEqualTo(201);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getId()).isPositive();
            softly.assertThat(response.getEmail()).isNull();
            softly.assertThat(response.getRole()).isEqualTo(UserRole.CLIENT);
            softly.assertThat(response.getStatus()).isEqualTo(UserStatus.ACTIVE);
            softly.assertThat(response.getFirstName()).isEmpty();
            softly.assertThat(response.getLastName()).isEmpty();
            softly.assertThat(response.getPhone()).isNull();
        });
    }
}
