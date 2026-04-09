package ru.shlapabank.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.models.response.RegisterResponse;
import ru.shlapabank.api.steps.AuthSteps;

/**
 * Базовый класс для тестов, требующих авторизованного пользователя.
 * Автоматически регистрирует пользователя перед тестами и удаляет его после.
 * <p>
 * Наследникам доступны поля {@code token} и {@code userId}.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AuthenticatedBaseTest extends BaseTest {

    protected final AuthSteps authSteps = new AuthSteps();

    protected String token;
    protected Integer userId;

    @BeforeAll
    void setUpUser() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();
        RegisterResponse registerResponse = authSteps.register(login, password);
        userId = registerResponse.getId();
        token = authSteps.login(login, password).getAccessToken();
    }

    @AfterAll
    void tearDownUser() {
        if (userId != null) {
            String adminToken = authSteps.login("admin", "admin").getAccessToken();
            authSteps.deleteUser(adminToken, userId);
        }
    }
}
