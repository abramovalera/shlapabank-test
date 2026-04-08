package ru.shlapabank.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shlapabank.enums.Endpoint;

import static io.restassured.http.ContentType.JSON;

class AuthRegistrationTest {


    @Test
    @DisplayName("Регистрация пользователя")
    void registerNewUser() {

        Response response = RestAssured
                .given()
                .baseUri(Endpoint.BASE_URL.getPath())
                .contentType(JSON)
                .body()
                .when()
                .post("/api/v1/auth/register")
                .then()
                .extract()
                .response();
    }
}
