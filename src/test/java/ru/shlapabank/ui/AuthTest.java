package ru.shlapabank.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.steps.AuthSteps;
import ru.shlapabank.ui.pages.AuthPage;

@Epic("UI")
@Feature("Авторизация и регистрация")
@DisplayName("Регистрация и авторизация")
class AuthTest extends UiBaseTest {

    private final AuthPage authPage = new AuthPage();
    private final AuthSteps authSteps = new AuthSteps();

    @Tag("smoke")
    @Test
    @DisplayName("Регистрация нового пользователя (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void registerUserTest() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();

        authPage.open()
                .register(login, password)
                .shouldBeLoggedIn();
    }

    @Tag("smoke")
    @Test
    @DisplayName("Авторизация существующего пользователя (POZITIVE)")
    @Severity(SeverityLevel.BLOCKER)
    void loginUserTest() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();

        authSteps.register(login, password);
        authPage.open()
                .login(login, password)
                .shouldBeLoggedIn();
    }
}