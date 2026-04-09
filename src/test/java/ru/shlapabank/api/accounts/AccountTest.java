package ru.shlapabank.api.accounts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.BaseTest;
import ru.shlapabank.api.generation.TestData;
import ru.shlapabank.api.models.response.AccountResponse;
import ru.shlapabank.api.steps.AccountSteps;
import ru.shlapabank.api.steps.AuthSteps;
import ru.shlapabank.api.steps.HelperSteps;
import ru.shlapabank.enums.AccountType;
import ru.shlapabank.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

import static ru.shlapabank.api.check.Checks.*;

@DisplayName("Счета")
class AccountTest extends BaseTest {

    private final AuthSteps authSteps = new AuthSteps();
    private final AccountSteps accountSteps = new AccountSteps();
    private final HelperSteps helperSteps = new HelperSteps();

    private String token;

    @BeforeEach
    void setUp() {
        String login = TestData.generateLogin();
        String password = TestData.defaultPassword();
        authSteps.register(login, password);
        token = authSteps.login(login, password).getAccessToken();
    }

    @Test
    @DisplayName("Открытие счета")
    void openAccountTest() {
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);

        assertPositive(account.getId(), "id счёта");
        assertNotBlank(account.getAccountNumber(), "номер счёта");
        assertEquals(account.getAccountType(), AccountType.DEBIT, "тип счёта");
        assertEquals(account.getCurrency(), Currency.RUB, "валюта");
        assertZero(account.getBalance(), "начальный баланс");
    }

    @Test
    @DisplayName("Пополнение счета")
    void addBalanceAccountTest() {
        AccountResponse account = accountSteps.openAccount(token, AccountType.DEBIT, Currency.RUB);
        BigDecimal amount = new BigDecimal("1000.00");

        String otp = helperSteps.getOtp(token);
        accountSteps.addBalance(token, account.getId(), amount, otp);

        List<AccountResponse> accounts = accountSteps.getAccounts(token);
        AccountResponse updated = accounts.stream()
                .filter(a -> a.getId().equals(account.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Счет не найден"));

        assertEquals(updated.getBalance(), amount, "баланс после пополнения");
    }
}