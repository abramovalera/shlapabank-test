package ru.shlapabank.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.models.response.RegisterResponse;
import ru.shlapabank.api.steps.AuthSteps;

/**
 * Базовый класс для тестов, требующих авторизованного пользователя.
 * На каждый тест — отдельный пользователь (ниже риск упираться в лимиты счетов на стенде).
 * Удаление пользователя в конце теста не валит прогон при сбое API.
 * <p>
 * Наследникам доступны поля {@code token} и {@code userId}.
 */
public abstract class AuthenticatedBaseTest extends BaseTest {

    protected final AuthSteps authSteps = new AuthSteps();

    protected String token;
    protected Integer userId;

    @BeforeEach
    void setUpUser() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();
        RegisterResponse registerResponse = authSteps.register(login, password);
        userId = registerResponse.getId();
        token = authSteps.login(login, password).getAccessToken();
    }

    @AfterEach
    void tearDownUser() {
        if (userId != null) {
            String adminToken = authSteps.login("admin", "admin").getAccessToken();
            authSteps.deleteUserQuietly(adminToken, userId);
        }
    }
}
