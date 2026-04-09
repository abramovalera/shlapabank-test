package ru.shlapabank.api.spec;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import ru.shlapabank.api.allure.AllureRestAssuredConfigurator;
import ru.shlapabank.config.AppConfig;

import static io.restassured.http.ContentType.JSON;

public final class ApiRequestSpec {

    private ApiRequestSpec() {}

    /**
     * Создает базовую спецификацию REST-запроса:
     * Allure-вложения, логирование, базовый URL и JSON-контент.
     *
     * @return базовая спецификация запроса
     */
    public static RequestSpecification base() {
        return RestAssured.given()
                .filter(AllureRestAssuredConfigurator.filterWithHtmlTemplates())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .baseUri(AppConfig.getBaseUrl())
                .accept(JSON)
                .contentType(JSON);
    }

    /**
     * Создает авторизованную спецификацию запроса
     * с заголовком Bearer-токена.
     *
     * @param token JWT access token
     * @return авторизованная спецификация запроса
     */
    public static RequestSpecification auth(String token) {
        return base()
                .header("Authorization", "Bearer " + token);
    }
}