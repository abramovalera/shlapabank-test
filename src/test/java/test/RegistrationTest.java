package test;

import enums.Role;
import enums.Status;
import io.restassured.response.Response;
import model.request.RegistrationRequest;
import model.response.RegistrationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    static Stream<Arguments> failedDataValue() {
        return Stream.of(
                Arguments.of(new RegistrationRequest("Bil77", "Password123!"), 422, "String should have at least 6 characters"
                ),
                Arguments.of(new RegistrationRequest("a".repeat(21), "Password123!"), 422, "String should have at most 20 characters"
                )
        );
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("failedDataValue")
    void failedRegistrationUserTest(RegistrationRequest data, int expectedStatus, String expectedMsg) {
        Response response = registrationSteps.registerFailed(data);

        assertEquals(expectedStatus, response.getStatusCode());
        assertTrue(response.getBody().asString().contains(expectedMsg),
                "Ответ должен содержать: " + expectedMsg);
    }
}
