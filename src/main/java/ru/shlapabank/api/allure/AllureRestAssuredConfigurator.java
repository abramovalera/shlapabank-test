package ru.shlapabank.api.allure;

import io.qameta.allure.restassured.AllureRestAssured;

/**
 * Создает фильтр {@link AllureRestAssured} с шаблонами {@code tpl/request.ftl} и {@code tpl/response.ftl}
 * для вложений запроса/ответа в отчет Allure.
 */
public final class AllureRestAssuredConfigurator {

    private AllureRestAssuredConfigurator() {
    }

    /** Новый фильтр для подключения через {@code .filter(...)}. */
    public static AllureRestAssured filterWithHtmlTemplates() {
        AllureRestAssured filter = new AllureRestAssured();
        filter.setRequestTemplate("request.ftl");
        filter.setResponseTemplate("response.ftl");
        filter.setResponseAttachmentName("Response");
        return filter;
    }
}

