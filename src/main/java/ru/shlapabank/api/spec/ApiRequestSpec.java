package ru.shlapabank.api.spec;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import ru.shlapabank.api.allure.AllureRestAssuredConfigurator;
import ru.shlapabank.enums.Endpoint;

import static io.restassured.http.ContentType.JSON;

/**
 * Общая {@link RequestSpecification} для API: фильтр Allure, лог запроса в консоль, base URI, JSON.
 */
public final class ApiRequestSpec {

    private ApiRequestSpec() {
    }

    public static RequestSpecification base() {
        return RestAssured.given()
                .filter(AllureRestAssuredConfigurator.filterWithHtmlTemplates())
                .log().all()
                .baseUri(Endpoint.BASE_URL.path())
                .accept(JSON)
                .contentType(JSON);
    }
}
