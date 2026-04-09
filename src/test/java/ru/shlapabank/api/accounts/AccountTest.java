package ru.shlapabank.api.accounts;

import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.UserCredentials;
import ru.shlapabank.api.models.request.AccountCreateRequest;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.api.steps.AccountApiSteps;
import ru.shlapabank.api.steps.AuthApiSteps;
import ru.shlapabank.api.steps.HelperApiSteps;


import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Счета")
class AccountTest {
    private final AuthApiSteps authApiSteps = new AuthApiSteps();
    private final AccountApiSteps accountApiSteps = new AccountApiSteps();
    private final HelperApiSteps helperApiSteps = new HelperApiSteps();
    private String token;

    @BeforeEach
    void setUp() {
        UserCredentials user = authApiSteps.generateUserCredentials();
        authApiSteps.register(user);
        token = authApiSteps.loginAndGetToken(user);
    }

    @Test
    @DisplayName("Открытие счета")
    void openAccountTest() {
        AccountCreateRequest accountRequest = new AccountCreateRequest(AccountType.DEBIT, Currency.RUB);
        Response rawResponse = accountApiSteps.openAccount(accountRequest, token);
        AccountResponse response = rawResponse.as(AccountResponse.class);

        Response accountsResponse = accountApiSteps.getAccounts(token);
        String accountsBody = accountsResponse.asString();

        assertThat(rawResponse.statusCode()).isEqualTo(201);
        assertThat(accountsResponse.statusCode()).isEqualTo(200);
        assertThat(accountsBody).contains(response.getAccountNumber());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getId()).isPositive();
            softly.assertThat(response.getAccountNumber()).isNotBlank();
            softly.assertThat(response.getAccountType()).isEqualTo(AccountType.DEBIT);
            softly.assertThat(response.getCurrency()).isEqualTo(Currency.RUB);
            softly.assertThat(response.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
            softly.assertThat(response.getIsPrimary()).isFalse();
        });
    }

    @Test
    @DisplayName("Пополнение счета")
    void addBalanceAccountTest() {
        BigDecimal amount = new BigDecimal("1000.00");

        AccountCreateRequest accountRequest = new AccountCreateRequest(AccountType.DEBIT, Currency.RUB);
        AccountResponse openedAccount = accountApiSteps.openAccount(accountRequest, token).as(AccountResponse.class);

        String otpCode = helperApiSteps.getOtpCode(token);
        Response rawResponse = accountApiSteps.topUpAccount(openedAccount.getId(), amount, otpCode, token);
        AccountResponse updatedAccount = accountApiSteps.getAccountById(openedAccount.getId(), token);

        assertThat(rawResponse.statusCode()).isEqualTo(201);
        assertThat(updatedAccount.getBalance()).isEqualByComparingTo(amount);
    }
}