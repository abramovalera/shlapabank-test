package ru.shlapabank.testsupport;

import org.aeonbits.owner.Config;

/**
 * Конфигурация окружения тестов 
 * <p>
 * Запуск: {@code -Denv=local} или {@code -Denv=web}. По умолчанию в Gradle для тестов подставляется {@code local}.
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:${env}.properties",
        "system:properties",
        "system:env"
})
public interface DataConfig extends Config {

    @Key("browser")
    @DefaultValue("chrome")
    String getBrowser();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String getBrowserSize();

    @Key("browserVersion")
    @DefaultValue("")
    String getBrowserVersion();

    /**
     * Базовый URL UI (Selenide). Пустое значение — взять тот же хост, что и API ({@code AppConfig#getUiUrl()}).
     */
    @Key("baseUrl")
    @DefaultValue("")
    String getBaseUrl();

    @Key("isRemote")
    @DefaultValue("false")
    boolean isRemote();

    @Key("remoteUrl")
    @DefaultValue("")
    String getRemoteUrl();
}
