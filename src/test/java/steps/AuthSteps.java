package steps;

import enums.Endpoint;
import model.request.AuthRequest;
import model.response.AuthResponse;
import specs.ApiSpecs;

import static io.restassured.RestAssured.given;

public class AuthSteps {

    public AuthResponse auth(AuthRequest data) {
        return given()
                .spec(ApiSpecs.baseRequestSpecs())
                .body(data)
                .post(Endpoint.LOGIN.getPath())
                .then()
                .spec(ApiSpecs.okResponseSpec())
                .extract()
                .as(AuthResponse.class);
    }
}
