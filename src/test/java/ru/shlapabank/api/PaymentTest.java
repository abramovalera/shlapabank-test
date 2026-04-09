package ru.shlapabank.api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.shlapabank.api.models.response.OperatorResponse;
import ru.shlapabank.api.models.response.VendorResponse;
import ru.shlapabank.api.steps.PaymentSteps;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("API")
@Feature("Платежи")
@DisplayName("Платежи")
class PaymentTest extends AuthenticatedBaseTest {

    private final PaymentSteps paymentSteps = new PaymentSteps();

    @Tag("smoke")
    @Test
    @DisplayName("GET Список мобильных операторов (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void getMobileOperatorsTest() {
        List<OperatorResponse> operators = paymentSteps.getMobileOperators(token);

        assertThat(operators).as("список операторов").isNotEmpty();
        assertThat(operators).allSatisfy(op -> {
            assertThat(op.getId()).as("id оператора").isNotBlank();
            assertThat(op.getName()).as("name оператора").isNotBlank();
        });
    }

    @Tag("smoke")
    @Test
    @DisplayName("GET Список поставщиков (POZITIVE)")
    @Severity(SeverityLevel.NORMAL)
    void getVendorProvidersTest() {
        List<VendorResponse> providers = paymentSteps.getVendorProviders(token);

        assertThat(providers).as("список поставщиков").isNotEmpty();
        assertThat(providers).allSatisfy(provider -> {
            assertThat(provider.getId()).as("id поставщика").isNotBlank();
            assertThat(provider.getName()).as("name поставщика").isNotBlank();
        });
    }
}
