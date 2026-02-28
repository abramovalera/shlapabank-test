package test;

import enums.Endpoint;
import enums.Role;
import enums.Status;
import io.restassured.http.ContentType;
import model.request.registrtion.RegistrationRequest;
import model.response.registration.RegistrationResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest extends BaseTest {

    @Test
    public void successfulRegistrationUserTest() {

        RegistrationRequest data = new RegistrationRequest(testData.login(), testData.password());
        RegistrationResponse response = given()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post(Endpoint.REGISTER.getPath())
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(RegistrationResponse.class);

        assertAll(
                () -> assertTrue(response.getId() > 0),
                () -> assertEquals(data.getLogin(), response.login),
                () -> assertEquals(response.getRole(), Role.CLIENT),
                () -> assertEquals(response.getStatus(), Status.ACTIVE)
        );
    }
}
