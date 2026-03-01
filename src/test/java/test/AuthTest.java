package test;

import enums.Role;
import model.request.AuthRequest;
import model.request.RegistrationRequest;
import model.response.AuthResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTest extends BaseTest {

    @Test
    void successfulUserAuthTest() {
        RegistrationRequest registrationRequest = testData.registrationRequest();
        registrationSteps.register(registrationRequest);

        AuthRequest authRequest = new AuthRequest(registrationRequest.getLogin(),
                registrationRequest.getPassword());
        AuthResponse authResponse = authSteps.auth(authRequest);

        String token = authResponse.getAccess_token();
        assertNotNull(token, "Токен null");
        assertFalse(token.isEmpty(), "Токен пустой");
    }

    @Test
    void successfulAdminAuthTest() {
        AuthRequest adminRequest = testData.authAdminRequest();
        AuthResponse authResponse = authSteps.auth(adminRequest);

        String token = authResponse.getAccess_token();
        assertNotNull(token, "Токен null");
        assertFalse(token.isEmpty(), "Токен пустой");
        assertEquals(Role.ADMIN, authResponse.getRole(), "Роль должна быть ADMIN");
    }
}
