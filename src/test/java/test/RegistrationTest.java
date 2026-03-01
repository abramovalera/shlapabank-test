package test;

import enums.Role;
import enums.Status;
import model.request.RegistrationRequest;
import model.response.RegistrationResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest extends BaseTest {

    @Test
    public void successfulRegistrationUserTest() {
        RegistrationRequest data = testData.registrationRequest();
        RegistrationResponse response = registrationSteps.register(data);

        assertAll(
                () -> assertTrue(response.getId() > 0),
                () -> assertEquals(data.getLogin(), response.getLogin()),
                () -> assertEquals(response.getRole(), Role.CLIENT),
                () -> assertEquals(response.getStatus(), Status.ACTIVE)
        );
    }
}
