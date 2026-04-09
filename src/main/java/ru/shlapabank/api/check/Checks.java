package ru.shlapabank.api.check;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;

public final class Checks {

    private Checks() {}

    @Step("Проверка: {field} положительный. Значение: {value}")
    public static void assertPositive(Number value, String field) {
        Assertions.assertThat(value.longValue()).isPositive();
    }

    @Step("Проверка: {field} не пустой. Значение: {value}")
    public static void assertNotBlank(String value, String field) {
        Assertions.assertThat(value).isNotBlank();
    }

    @Step("Проверка: {field}. Ожидается: '{expected}', получено: '{actual}'")
    public static void assertEquals(Object actual, Object expected, String field) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Step("Проверка: {field} = 0. Значение: {value}")
    public static void assertZero(BigDecimal value, String field) {
        Assertions.assertThat(value).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Step("Проверка: {field}. Ожидается: '{expected}', получено: '{actual}'")
    public static void assertEquals(BigDecimal actual, BigDecimal expected, String field) {
        Assertions.assertThat(actual).isEqualByComparingTo(expected);
    }
}