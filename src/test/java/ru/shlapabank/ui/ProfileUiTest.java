package ru.shlapabank.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.models.response.ProfileResponse;
import ru.shlapabank.api.steps.ProfileSteps;
import ru.shlapabank.ui.pages.DashboardPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
@Epic("UI")
@Feature("Профиль")
@DisplayName("Профиль пользователя")
class ProfileUiTest extends UiAuthenticatedBaseTest {

    private final DashboardPage dashboard = new DashboardPage();
    private final ProfileSteps profileSteps = new ProfileSteps();

    @Tag("smoke")
    @Test
    @DisplayName("Обновление профиля (POZITIVE)")
    @Severity(SeverityLevel.CRITICAL)
    void updateProfileTest() {
        String email = TestData.generateUniqueEmail();
        dashboard.shouldBeLoaded()
                .updateProfile("Ivan", email);

        ProfileResponse profile = profileSteps.getProfile(token);
        assertThat(profile.getFirstName()).isEqualTo("Ivan");
        assertThat(profile.getEmail()).isEqualTo(email);
    }
}
