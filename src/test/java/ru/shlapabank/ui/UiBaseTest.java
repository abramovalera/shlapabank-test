package ru.shlapabank.ui;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.shlapabank.api.BaseTest;
import ru.shlapabank.testsupport.WebDriverConfig;
import ru.shlapabank.ui.helpers.Attach;

/**
 * UI-тесты наследуют {@link BaseTest}: перед запуском вызывается {@code GET /health} на {@code AppConfig#getBaseUrl()}.
 * Если фронт доступен при недоступном API, сценарии всё равно будут пропущены из-за этой проверки.
 */
public abstract class UiBaseTest extends BaseTest {

    @BeforeAll
    static void configureWebDriverFromEnv() {
        if (System.getProperty("env") == null || System.getProperty("env").isBlank()) {
            System.setProperty("env", "local");
        }
        new WebDriverConfig().configParams();
    }

    @BeforeEach
    void setUpBrowser() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void tearDownBrowser() {
        Attach.attachUiArtifacts();
        SelenideLogger.removeListener("AllureSelenide");
        Selenide.closeWebDriver();
    }
}