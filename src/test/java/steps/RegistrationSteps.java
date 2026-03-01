package steps;

import enums.Endpoint;
import io.restassured.response.Response;
import model.request.RegistrationRequest;
import model.response.RegistrationResponse;
import specs.ApiSpecs;

import static io.restassured.RestAssured.given;

public class RegistrationSteps {

    /**
     * Шаг успешной регистрации
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

    /**
     * Отправка запроса регистрации без проверки статуса (для негативных тестов).
     *
     * @param data тело запроса
     * @return ответ сервера (проверять statusCode в тесте)
     */
    public Response registerFailed(RegistrationRequest data) {
        return given()
                .spec(ApiSpecs.baseRequestSpecs())
                .body(data)
                .post(Endpoint.REGISTER.getPath())
                .then()
                .extract()
                .response();
    }
}
