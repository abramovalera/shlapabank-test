package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Ответ {@code GET .../transfers/daily-usage} с вложенным {@code limits.perCurrency}.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyUsageResponse {
    private LimitsBlock limits;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LimitsBlock {
        private List<PerCurrencyRow> perCurrency;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PerCurrencyRow {
        private String currency;
        private String dailyLimit;
        private String usedToday;
        private String remaining;
    }

    /** Остаток дневного лимита по коду валюты. */
    public Map<String, BigDecimal> remainingByCurrency() {
        if (limits == null || limits.getPerCurrency() == null) {
            return Map.of();
        }
        return limits.getPerCurrency().stream()
                .filter(r -> r.getCurrency() != null && r.getRemaining() != null)
                .collect(Collectors.toMap(
                        PerCurrencyRow::getCurrency,
                        r -> new BigDecimal(r.getRemaining())));
    }
}
