package ru.shlapabank.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.shlapabank.api.spec.ApiRequestSpec;

public class ApiClient {

    /**
     * Отправляет POST запрос.
     *
     * @param path эндпоинт
     * @param body тело запроса
     * @return ответ
     */
    public Response post(String path, Object body) {
        return RestAssured.given(ApiRequestSpec.base())
                .body(body)
                .when()
                .post(path)
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Отправляет авторизованный POST запрос.
     *
     * @param path        эндпоинт
     * @param body        тело запроса
     * @param accessToken bearer токен
     * @return ответ
     */
    public Response post(String path, Object body, String accessToken) {
        return RestAssured.given(ApiRequestSpec.base())
                .auth()
                .oauth2(accessToken)
                .body(body)
                .when()
                .post(path)
                .then()
                .log().all()
                .extract()
                .response();
    }

    /**
     * Отправляет авторизованный POST запрос.
     *
     * @param path        эндпоинт
     * @param accessToken bearer токен
     * @return ответ
     */
    public Response get(String path,String accessToken) {
        return RestAssured.given(ApiRequestSpec.base())
                .auth()
                .oauth2(accessToken)
                .when()
                .get(path)
                .then()
                .log().all()
                .extract()
                .response();
    }
}
