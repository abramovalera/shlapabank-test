package ru.shlapabank.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.models.request.ProfileUpdateRequest;
import ru.shlapabank.api.models.response.ProfileResponse;
import ru.shlapabank.api.steps.ProfileSteps;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("API")
@Feature("Профиль")
@DisplayName("Профиль пользователя")
class ProfileTest extends AuthenticatedBaseTest {

    private final ProfileSteps profileSteps = new ProfileSteps();

    @Tag("smoke")
    @Test
    @DisplayName("PUT Обновление профиля (POZITIVE)")
    @Severity(SeverityLevel.CRITICAL)
    void updateProfileTest() {
        ProfileUpdateRequest request = ProfileUpdateRequest.builder()
                .firstName("Ivan")
                .email("ivan@shlapabank.ru")
                .build();

        ProfileResponse updated = profileSteps.updateProfile(token, request);

        assertThat(updated.getFirstName()).as("first_name").isEqualTo("Ivan");
        assertThat(updated.getEmail()).as("email").isEqualTo("ivan@shlapabank.ru");
    }
}
