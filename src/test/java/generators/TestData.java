package generators;

import config.AdminCredentials;
import model.request.AuthRequest;
import model.request.RegistrationRequest;
import net.datafaker.Faker;

public class TestData {

    private final Faker faker = new Faker();

    public String login() {
        return faker.regexify("[A-Za-z0-9]{6,20}");
    }

    public String password() {
        return faker.credentials().password(8, 30, true, true);
    }

    /**
     * Метод возвращает сгенерированный: <br>
     * login, password
     */
    public RegistrationRequest registrationRequest() {
        return new RegistrationRequest(login(), password());
    }

    public AuthRequest authAdminRequest() {
        return new AuthRequest(AdminCredentials.login(), AdminCredentials.password());
    }
}
