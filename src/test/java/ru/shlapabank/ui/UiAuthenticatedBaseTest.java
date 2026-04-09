package ru.shlapabank.ui;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.models.response.RegisterResponse;
import ru.shlapabank.api.steps.AuthSteps;
import ru.shlapabank.ui.pages.AuthPage;

/**
 * UI-тесты под авторизованным пользователем: пользователь создаётся через API (как в {@link ru.shlapabank.api.AuthenticatedBaseTest}),
 * перед каждым тестом выполняется вход через UI.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class UiAuthenticatedBaseTest extends UiBaseTest {

    protected final AuthSteps authSteps = new AuthSteps();
    protected final AuthPage authPage = new AuthPage();

    protected String login;
    protected String password;
    protected Integer userId;
    /** Токен для API-подготовки данных в тех же тестах. */
    protected String token;

    @BeforeAll
    void uiAuthSetUpUser() {
        login = TestData.generateLogin();
        password = TestData.defaultPassword();
        RegisterResponse reg = authSteps.register(login, password);
        userId = reg.getId();
        token = authSteps.login(login, password).getAccessToken();
    }

    @AfterAll
    void uiAuthTearDownUser() {
        if (userId != null) {
            String adminToken = authSteps.login("admin", "admin").getAccessToken();
            authSteps.deleteUser(adminToken, userId);
        }
    }

    @BeforeEach
    void logInViaUi() {
        authPage.open().login(login, password).shouldBeLoggedIn();
    }
}
