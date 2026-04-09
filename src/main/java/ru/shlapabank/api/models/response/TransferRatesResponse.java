package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Модель ответа по курсам валют.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferRatesResponse {
    @JsonIgnore
    private final Map<String, Object> rates = new HashMap<>();

    @JsonAnySetter
    public void put(String key, Object value) {
        rates.put(key, value);
    }
}
