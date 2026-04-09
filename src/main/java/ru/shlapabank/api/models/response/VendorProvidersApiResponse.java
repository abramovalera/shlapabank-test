package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Модель ответа со списком поставщиков для платежей.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorProvidersApiResponse {
    private Long userId;
    private List<VendorProviderItemResponse> providers;
    private Map<String, Object> amountRangeRub;
}
