package ru.shlapabank.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponse {
    private Long id;
    private String type;
    private TransactionMoney money;
    private String description;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("from_account_id")
    private Long fromAccountId;
    @JsonProperty("to_account_id")
    private Long toAccountId;
    private String status;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransactionMoney {
        private String amount;
        private String fee;
        private String total;
        private String currency;
    }
}
