package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Модель ответа со списком мобильных операторов для платежей.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileOperatorsApiResponse {
    private Long userId;
    private List<String> operators;
    private Map<String, Object> amountRangeRub;
}
