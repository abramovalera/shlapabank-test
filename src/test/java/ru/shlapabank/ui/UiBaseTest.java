package ru.shlapabank.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.shlapabank.api.BaseTest;
import ru.shlapabank.config.AppConfig;
import ru.shlapabank.ui.helpers.Attach;

public abstract class UiBaseTest extends BaseTest {

    @BeforeEach
    @Step("Открытие браузера")
    void setUpBrowser() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.baseUrl = AppConfig.getUiUrl();
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserSize = System.getProperty("browser.size", "1920x1080");
        Configuration.pageLoadTimeout = 15_000;

        String remoteUrl = System.getProperty("selenide.remote");
        if (remoteUrl != null && !remoteUrl.isBlank()) {
            Configuration.remote = remoteUrl;
        }
    }

    @AfterEach
    void tearDownBrowser() {
        Attach.attachUiArtifacts();
        SelenideLogger.removeListener("AllureSelenide");
        Selenide.closeWebDriver();
    }
}