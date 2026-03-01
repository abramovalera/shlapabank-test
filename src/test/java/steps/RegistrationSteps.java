package steps;

import enums.Endpoint;
import model.request.RegistrationRequest;
import model.response.RegistrationResponse;
import specs.ApiSpecs;

import static io.restassured.RestAssured.given;

public class RegistrationSteps {

    /**
     * Шаг регистрации
     *
     * @param data тело запроса
     * @return {@link RegistrationResponse}
     */
    public RegistrationResponse register(RegistrationRequest data) {
        return given()
                .spec(ApiSpecs.baseRequestSpecs())
                .body(data)
                .post(Endpoint.REGISTER.getPath())
                .then()
                .spec(ApiSpecs.createdResponseSpec())
                .extract()
                .as(RegistrationResponse.class);
    }
}
