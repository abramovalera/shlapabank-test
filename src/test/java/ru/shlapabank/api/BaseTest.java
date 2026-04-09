package ru.shlapabank.api;

import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public abstract class BaseTest {

    @BeforeAll
    static void checkServer() {
        boolean isAlive = isServerAlive();
        assumeTrue(isAlive, "Сервер недоступен тесты будут пропущены");
    }

    @Step("Проверка доступности сервера")
    private static boolean isServerAlive() {
        try {
            int statusCode = given()
                    .baseUri(AppConfig.getBaseUrl())
                    .get("/health")
                    .getStatusCode();
            return statusCode == 200;
        } catch (Exception e) {
            return false;
        }
    }
}