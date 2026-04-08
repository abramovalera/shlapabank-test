package ru.shlapabank.api.auth;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.SoftAssertions;
import ru.shlapabank.api.ApiClient;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.models.request.RegisterRequest;
import ru.shlapabank.api.models.response.RegisterResponse;
import ru.shlapabank.enums.Endpoint;
import ru.shlapabank.enums.UserRole;
import ru.shlapabank.enums.UserStatus;

@DisplayName("Регистрация")
class RegistrationTest {
    private final ApiClient apiClient = new ApiClient();

    @Test
    @DisplayName("Регистрация пользователя")
    void registerNewUser() {
        RegisterRequest request = new RegisterRequest(TestData.generateLogin(), TestData.defaultPassword());
        Response rawResponse = apiClient.post(Endpoint.REGISTER.path(), request);

        RegisterResponse response = rawResponse.as(RegisterResponse.class);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rawResponse.statusCode()).isEqualTo(201);
            softly.assertThat(response.getId()).isPositive();
            softly.assertThat(response.getEmail()).isNull();
            softly.assertThat(response.getRole()).isEqualTo(UserRole.CLIENT);
            softly.assertThat(response.getStatus()).isEqualTo(UserStatus.ACTIVE);
            softly.assertThat(response.getFirst_name()).isEmpty();
            softly.assertThat(response.getLast_name()).isEmpty();
            softly.assertThat(response.getPhone()).isNull();
        });
    }
}
