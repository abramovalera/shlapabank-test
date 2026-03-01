package specs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static enums.Endpoint.BASE_URL;

public class ApiSpecs {

    private ApiSpecs() {
    }

    /**
     * Базовая спецификация запроса для Api запроса.
     */
    public static RequestSpecification baseRequestSpecs() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL.getPath())
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new AllureRestAssured()
                        .setRequestTemplate("http-request.ftl")
                        .setResponseTemplate("http-response.ftl"))
                .build();
    }

    /**
     * Спецификация для успешного создания (201 Created)
     */
    public static ResponseSpecification createdResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_CREATED)
                .expectContentType(ContentType.JSON)
                .build();
    }

    /**
     * Спецификация для успешного ответа (200 OK)
     */
    public static ResponseSpecification okResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
