package ru.shlapabank.api.allure;

import io.qameta.allure.restassured.AllureRestAssured;

/**
 * Создает фильтр {@link AllureRestAssured} с HTML-шаблонами из classpath {@code tpl/*.ftl}.
 */
public final class AllureRestAssuredConfigurator {

    private AllureRestAssuredConfigurator() {
    }

    /** Новый фильтр для подключения через {@code .filter(...)}. */
    public static AllureRestAssured filterWithHtmlTemplates() {
        AllureRestAssured filter = new AllureRestAssured();
        filter.setRequestTemplate("tpl/request.ftl");
        filter.setResponseTemplate("tpl/response.ftl");
        filter.setResponseAttachmentName("Response");
        return filter;
    }
}

