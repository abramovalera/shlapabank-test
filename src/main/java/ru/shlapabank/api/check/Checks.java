package ru.shlapabank.api.check;

import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import ru.shlapabank.api.models.response.TransactionResponse;

import java.math.BigDecimal;

public final class Checks {

    private Checks() {}

    /**
     * Проверяет, что транзакция завершена успешно и содержит базовые поля.
     */
    @Step("Проверка завершённой транзакции: тип={expectedType}")
    public static void assertCompletedTransaction(TransactionResponse tx, String expectedType) {
        Assertions.assertThat(tx.getStatus()).as("статус транзакции").isEqualTo("COMPLETED");
        Assertions.assertThat(tx.getType()).as("тип транзакции").isEqualTo(expectedType);
        Assertions.assertThat(tx.getId()).as("id транзакции").isPositive();
        Assertions.assertThat(tx.getMoney()).as("блок money").isNotNull();
        Assertions.assertThat(tx.getCreatedAt()).as("created_at").isNotBlank();
    }

    /**
     * Проверяет, что комиссия транзакции равна нулю.
     */
    @Step("Проверка: комиссия = 0")
    public static void assertZeroFee(TransactionResponse tx) {
        Assertions.assertThat(new BigDecimal(tx.getMoney().getFee()))
                .as("комиссия должна быть 0")
                .isEqualByComparingTo(BigDecimal.ZERO);
    }
}
