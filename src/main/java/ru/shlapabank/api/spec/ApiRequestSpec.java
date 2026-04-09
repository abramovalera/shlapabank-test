package ru.shlapabank.api.spec;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import ru.shlapabank.api.AppConfig;
import ru.shlapabank.api.allure.AllureRestAssuredConfigurator;

import static io.restassured.http.ContentType.JSON;

public final class ApiRequestSpec {

    private ApiRequestSpec() {
    }

    public static RequestSpecification base() {
        return RestAssured.given()
                .filter(AllureRestAssuredConfigurator.filterWithHtmlTemplates())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .baseUri(AppConfig.getBaseUrl())
                .accept(JSON)
                .contentType(JSON);
    }

    public static RequestSpecification auth(String token) {
        return base()
                .header("Authorization", "Bearer " + token);
    }
}