package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Модель ответа по суточным лимитам переводов.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyUsageResponse {
    @JsonIgnore
    private final Map<String, Object> usage = new HashMap<>();

    @JsonAnySetter
    public void put(String key, Object value) {
        usage.put(key, value);
    }
}
