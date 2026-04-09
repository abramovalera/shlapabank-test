package ru.shlapabank.testsupport;

import com.codeborne.selenide.Configuration;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.MutableCapabilities;
import ru.shlapabank.config.AppConfig;

import java.util.Map;

/**
 * Настройка Selenide из Owner-конфига (classpath:{@code ${env}.properties}).
 */
public final class WebDriverConfig {

    private final DataConfig config = ConfigFactory.create(DataConfig.class, System.getProperties());

    public void configParams() {
        String base = config.getBaseUrl();
        Configuration.baseUrl = (base == null || base.isBlank())
                ? trimTrailingSlash(AppConfig.getUiUrl())
                : base.trim();
        Configuration.browser = config.getBrowser().trim().toLowerCase();
        Configuration.browserSize = config.getBrowserSize();
        String version = config.getBrowserVersion();
        if (version != null && !version.isBlank()) {
            Configuration.browserVersion = version.trim();
        }
        Configuration.pageLoadTimeout = 20_000;
        Configuration.timeout = 15_000;

        if (config.isRemote()) {
            String hub = config.getRemoteUrl();
            if (hub == null || hub.isBlank()) {
                throw new IllegalStateException("isRemote=true, но remoteUrl пустой — укажите remoteUrl в *.properties");
            }
            Configuration.remote = hub;
            MutableCapabilities capabilities = new MutableCapabilities();
            Configuration.browserCapabilities = capabilities;
            capabilities.setCapability(
                    "selenoid:options",
                    Map.<String, Object>of(
                            "enableVNC", true,
                            "enableVideo", true
                    ));
        } else {
            Configuration.remote = null;
            // Selenide 7.x сливает browserCapabilities в ChromeDriverFactory; null даёт NPE в
            // AbstractDriverFactory.verifyItsSameBrowser (вызов extra.getBrowserName()).
            Configuration.browserCapabilities = new MutableCapabilities();
        }
    }

    private static String trimTrailingSlash(String url) {
        if (url == null || url.length() <= 1) {
            return url;
        }
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
