package ru.shlapabank.api.accounts;

import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.generation.UserCredentials;
import ru.shlapabank.api.models.request.AccountCreateRequest;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.api.models.response.TokenResponse;
import ru.shlapabank.api.steps.AccountApiSteps;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;
import ru.shlapabank.api.steps.AuthApiSteps;

@DisplayName("Счета")
class AccountTest {

    private final AuthApiSteps authApiSteps = new AuthApiSteps();
    private final AccountApiSteps accountApiSteps = new AccountApiSteps();

    @Test
    @DisplayName("Открытие счета")
    void openAccount() {
        UserCredentials user = authApiSteps.generateUserCredentials();

        Response authResponse = authApiSteps.registerAndLogin(user.getLogin(), user.getPassword());
        TokenResponse tokenResponse = authResponse.as(TokenResponse.class);

        AccountCreateRequest accountRequest = new AccountCreateRequest(AccountType.DEBIT, Currency.RUB);
        Response rawResponse = accountApiSteps.openAccount(accountRequest, tokenResponse.getAccess_token());
        AccountResponse response = rawResponse.as(AccountResponse.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rawResponse.statusCode()).isEqualTo(201);
            softly.assertThat(response.getId()).isPositive();
            softly.assertThat(response.getAccount_number()).isNotBlank();
            softly.assertThat(response.getAccount_type()).isEqualTo(AccountType.DEBIT);
            softly.assertThat(response.getCurrency()).isEqualTo(Currency.RUB);
            softly.assertThat(response.getBalance()).isNotPositive();
            softly.assertThat(response.getIs_primary()).isFalse();
        });
    }
}
