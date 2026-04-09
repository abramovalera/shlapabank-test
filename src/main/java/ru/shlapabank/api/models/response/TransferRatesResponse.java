package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collections;
import java.util.Map;

/**
 * Ответ {@code GET .../transfers/rates}: курсы в {@code toRub}.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferRatesResponse {
    private Long userId;
    private String base;

    @JsonProperty("toRub")
    private Map<String, String> toRub = Collections.emptyMap();

    /** Курсы к рублю по коду валюты. */
    public Map<String, String> getRates() {
        return toRub != null ? toRub : Collections.emptyMap();
    }
}
