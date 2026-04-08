package ru.shlapabank.api.generation;

import com.github.javafaker.Faker;

public final class TestData {

    private static final Faker FAKER = new Faker();
    private static final String DEFAULT_PASSWORD = "Password@123";

    private TestData() {
    }

    /**
     * Логин по контракту API: только [A-Za-z0-9], длина 6–20.
     */
    public static String generateLogin() {
        String prefix = "user";
        String suffix = FAKER.regexify("[a-zA-Z0-9]{8}");
        String login = prefix + suffix;
        if (login.length() > 20) {
            login = login.substring(0, 20);
        }
        return login;
    }

    public static String defaultPassword() {
        return DEFAULT_PASSWORD;
    }
}
