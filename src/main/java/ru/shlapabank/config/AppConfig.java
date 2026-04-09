package ru.shlapabank.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {

    private static final Properties PROPERTIES = loadProperties();

    private AppConfig() {}

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = AppConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is != null) props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить config.properties", e);
        }
        return props;
    }

    public static String getBaseUrl() {
        String url = System.getProperty("base.url");
        if (url != null) return url;
        url = PROPERTIES.getProperty("base.url");
        if (url == null) throw new RuntimeException("base.url не задан ни в config.properties, ни через -Dbase.url");
        return url;
    }

    public static String getUiUrl() {
        String url = System.getProperty("ui.url");
        if (url != null && !url.isBlank()) return url;
        return getBaseUrl();
    }
}