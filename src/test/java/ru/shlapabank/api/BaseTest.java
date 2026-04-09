package ru.shlapabank.api;

import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import ru.shlapabank.config.AppConfig;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public abstract class BaseTest {

    @BeforeAll
    static void checkServerIsAlive() {
        assumeTrue(isServerAlive(), "Сервер недоступен — тесты пропущены: " + AppConfig.getBaseUrl());
    }

    @Step("Проверка доступности сервера")
    private static boolean isServerAlive() {
        try {
            int status = given()
                    .baseUri(AppConfig.getBaseUrl())
                    .get("/health")
                    .getStatusCode();
            return status == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
