package ru.shlapabank.api.steps;

import io.qameta.allure.Step;
import ru.shlapabank.api.models.response.MobileOperatorsApiResponse;
import ru.shlapabank.api.models.response.OperatorResponse;
import ru.shlapabank.api.models.response.VendorProvidersApiResponse;
import ru.shlapabank.api.models.response.VendorResponse;
import ru.shlapabank.api.spec.ApiRequestSpec;
import ru.shlapabank.enums.Endpoint;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PaymentSteps {

    @Step("Получение списка мобильных операторов")
    public List<OperatorResponse> getMobileOperators(String token) {
        MobileOperatorsApiResponse body = ApiRequestSpec.auth(token)
                .get(Endpoint.PAYMENTS_MOBILE_OPERATORS.path())
                .then()
                .statusCode(200)
                .extract()
                .as(MobileOperatorsApiResponse.class);
        List<String> names = body.getOperators();
        if (names == null) {
            return List.of();
        }
        return IntStream.range(0, names.size())
                .mapToObj(i -> {
                    String name = names.get(i);
                    String id = name != null ? name : "op-" + i;
                    return new OperatorResponse(id, Objects.requireNonNullElse(name, ""));
                })
                .collect(Collectors.toList());
    }

    @Step("Получение списка поставщиков")
    public List<VendorResponse> getVendorProviders(String token) {
        VendorProvidersApiResponse body = ApiRequestSpec.auth(token)
                .get(Endpoint.PAYMENTS_VENDOR_PROVIDERS.path())
                .then()
                .statusCode(200)
                .extract()
                .as(VendorProvidersApiResponse.class);
        if (body.getProviders() == null) {
            return List.of();
        }
        return body.getProviders().stream()
                .map(p -> {
                    String name = p.getName() != null ? p.getName() : "";
                    String id = p.getAccountLength() != null ? name + ":" + p.getAccountLength() : name;
                    return new VendorResponse(id, name);
                })
                .collect(Collectors.toList());
    }
}
