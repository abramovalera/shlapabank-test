package ru.shlapabank.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {

    private static final Properties PROPERTIES = loadProperties();

    private AppConfig() {}

    private static Properties loadProperties() {
        Properties props = new Properties();
        try (InputStream is = AppConfig.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить config.properties", e);
        }
        return props;
    }

    public static String getBaseUrl() {
        // сначала смотрим системное свойство, потом файл
        String url = System.getProperty("base.url");
        return url != null ? url : PROPERTIES.getProperty("base.url");
    }
}