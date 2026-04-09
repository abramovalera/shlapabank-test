package ru.shlapabank.api.check;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

public final class Checks {

    private Checks() {}

    @Step("Проверка: {field} положительный")
    public static void assertPositive(Number value, String field) {
        Assertions.assertThat(value.longValue()).isPositive();
    }

    @Step("Проверка: {field} не пустой")
    public static void assertNotBlank(String value, String field) {
        Assertions.assertThat(value).isNotBlank();
    }

    @Step("Проверка: {field} = '{expected}'")
    public static void assertEquals(Object actual, Object expected, String field) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Step("Проверка: {field} = 0")
    public static void assertZero(BigDecimal value, String field) {
        Assertions.assertThat(value).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Step("Проверка: {field} = {expected}")
    public static void assertEquals(BigDecimal actual, BigDecimal expected, String field) {
        Assertions.assertThat(actual).isEqualByComparingTo(expected);
    }
}