package ru.shlapabank.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AuthPage {

    private final SelenideElement loginInput = $("[data-testid='input-login']");
    private final SelenideElement registerLink = $("[data-testid='link-register']");
    private final SelenideElement passwordInput = $("[data-testid='input-password']");
    private final SelenideElement loginButton = $("[data-testid='btn-login']");
    private final SelenideElement registerLoginInput = $("[data-testid='input-reg-login']");
    private final SelenideElement registerPasswordInput = $("[data-testid='input-reg-password']");
    private final SelenideElement registerButton = $("[data-testid='btn-register']");
    private final SelenideElement logoutButton = $("[data-testid='btn-logout']");

    @Step("Открываем страницу авторизации")
    public AuthPage open() {
        Selenide.open("/login");
        return this;
    }

    @Step("Регистрируемся: логин={login}")
    public AuthPage register(String login, String password) {
        registerLink.click();
        registerLoginInput.setValue(login);
        registerPasswordInput.setValue(password);
        registerButton.click();
        return this;
    }

    @Step("Входим в систему: логин={login}")
    public AuthPage login(String login, String password) {
        loginInput.setValue(login);
        passwordInput.setValue(password);
        loginButton.click();
        return this;
    }

    @Step("Пользователь авторизован")
    public AuthPage shouldBeLoggedIn() {
        logoutButton.shouldBe(visible, Duration.ofSeconds(20));
        return this;
    }

}